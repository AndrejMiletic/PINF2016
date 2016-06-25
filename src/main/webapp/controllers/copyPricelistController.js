app.controller('copyPricelistController', ['$scope', '$window', 'tableService', function($scope, $window, tableService){
		function init(){
	
		 tableService.getTableByName("Cenovnik").then(
			function (response) {
				$scope.requestedTable = response.data;
            },
			function (response) {
				alert("Greska");
			}
		);
		 	
			$scope.modelArray = [];
	}

	init();
	
	 $scope.openDocumentPriceList = function () {
		
		 
		 if($scope.selectedPricelist){
		 
			 for (var index in $scope.requestedTable.rows) {

				 if($scope.requestedTable.rows[index].fields["Naziv cenovnika"] == $scope.selectedPricelist.trim()){
					 		
						 $scope.selectedData = $scope.requestedTable.rows[index];
					 	 
						 tableService.getDocChild($scope.requestedTable.tableName, $scope.requestedTable.rows[index].fields.Id).then(
							function (response) {
								$scope.documentChild = response.data;
								if($scope.documentChild.rows.length != 0){
										$scope.parentID = $scope.documentChild.rows[0].fields.cenovnik;
										$scope.date =  $scope.selectedData.fields["Datum primene"];
										console.log($scope.selectedData.fields["Datum primene"])
								}
							},
							function (response) {
								alert("Neuspesno dobavljanje tabele");
							}
						);
				 }

			}
		 }else{
			 alert("Izaberite cenovnik iz liste!");
		 }
		 
    };
	
	$scope.apply = function(row){
	
		var isValid;		
	    var number = /^[0-9.-]+$/;
		
		for (var index in $scope.documentChild.rows) {
			 
			 if($scope.documentChild.rows[index].fields == row && $scope.modelArray[index]){
				
				  if($scope.modelArray[index].match(number)) 
				  {  
					 isValid = true;  
				  } else{
					 isValid = false;
					    }
				 if(isValid){
					var newPrice = $scope.modelArray[index];
					var oldPrice = $scope.documentChild.rows[index].fields["Jedinična cena stavke"];
					
					$scope.documentChild.rows[index].fields["Jedinična cena stavke"] = parseFloat(oldPrice) +parseFloat($scope.documentChild.rows[index].fields["Jedinična cena stavke"]*newPrice/100);
					$scope.modelArray[index] = 0;
				 }else{
					 alert("Unesite broj!");
					 return;
				      }
			 }
			
		}	
	}
	$scope.copyPricelist = function(){
		
		if($scope.selectedData.fields["Datum primene"] == $scope.date){
			alert("Promeni datum primene cenovnika.");
		}else{
				var pricelist = {
					parent :  $scope.selectedData,
					child : $scope.documentChild.rows
				}

				tableService.addPricelist(pricelist).then(
						function (response) {
							 tableService.getTableByName("Cenovnik").then(
								function (response) {
									alert("Uspešno kopiran cenovnik");
									$scope.requestedTable = response.data;
								},
								function (response) {
									alert("Greska");
								}
							);
						},
						function (response) {
							alert("Greska");
						}
					);
		}
	};

}]);