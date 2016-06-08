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
	}

	init();
	
	 $scope.openDocument = function () {
		 
		 if($scope.selectedPricelist){
		 
			 for (var index in $scope.requestedTable.rows) {

				 if($scope.requestedTable.rows[index].fields.naziv == $scope.selectedPricelist.trim()){
						 tableService.getDocChild($scope.requestedTable.tableName, $scope.requestedTable.rows[index].fields.id).then(
							function (response) {
								$scope.documentChild = response.data;
								$scope.documentChildCopy = response.data;
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
			 
			 if($scope.documentChild.rows[index].fields == row){
				
				  if( document.getElementById($scope.documentChild.rows[index].fields.id).value.trim().match(number)) 
				  {  
					 isValid = true;  
				  } else{
					 isValid = false;
					    }
				 if(isValid){
					var newPrice = document.getElementById($scope.documentChild.rows[index].fields.id).value.trim();
					var oldPrice = $scope.documentChild.rows[index].fields.vrednost;
					$scope.documentChild.rows[index].fields.vrednost = parseFloat(oldPrice) +parseFloat($scope.documentChild.rows[index].fields.vrednost*newPrice/100);
					document.getElementById($scope.documentChild.rows[index].fields.id).value = null;
				 }else{
					 alert("Unesite broj!");
					 return;
				      }
			 }
			
		}	
	}
	$scope.copyPricelist = function(){
		
	}

}]);