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
					 		
						 $scope.selectedData = $scope.requestedTable.rows[index];
					 	 document.getElementById('input').value = $scope.selectedData.fields.datum_primene;
					 	 
						 tableService.getDocChild($scope.requestedTable.tableName, $scope.requestedTable.rows[index].fields.id).then(
							function (response) {
								$scope.documentChild = response.data;
								$scope.parentID = $scope.documentChild.rows[0].fields.parentId;
								$scope.date =  $scope.selectedData.fields.datum_primene;
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
					var oldPrice = $scope.documentChild.rows[index].fields.jedinicna_cena;
					
					$scope.documentChild.rows[index].fields.jedinicna_cena = parseFloat(oldPrice) +parseFloat($scope.documentChild.rows[index].fields.jedinicna_cena*newPrice/100);
					document.getElementById($scope.documentChild.rows[index].fields.id).value = null;
				 }else{
					 alert("Unesite broj!");
					 return;
				      }
			 }
			
		}	
	}
	$scope.copyPricelist = function(){
		if($scope.date === document.getElementById('input').value.trim()){
			alert("Mora se promeniti bar datum.");
		}else{
			if($scope.isDate(document.getElementById('input').value.trim())){
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
		}
	};
	$scope.showForm = function(){
	 	tableService.getTableByName("Katalog").then(
			function (response) {
				$scope.catalog = response.data;
            },
			function (response) {
				alert("Greska");
			}
		);
		$scope.form = true;
	}
	$scope.addArticle = function(){
		
		
		for (var index in $scope.catalog.rows) {
			 var row = {};
			 if($scope.catalog.rows[index].fields.naziv_artikla == $scope.selectedArticle.trim()){
			
				 for (var index1 in $scope.documentChild.rows) {
					
					 if($scope.documentChild.rows[index1].fields.id_artikla == $scope.catalog.rows[index].fields.id_artikla){
						 alert("Artikal vec postoji!");
						 return;
					 }
				}
				
					var fields = {
						"id" : "",
						parentId : $scope.parentID,
						id_artikla : $scope.catalog.rows[index].fields.id_artikla,
						jedinicna_cena : $scope.catalog.rows[index].fields.jedinicna_cena
					}
				row.fields = fields;
				 
				tableService.addTableRow(row).then(
					function (response) {
					},
					function (response) {
						alert("Greska");
					}
				);
			 }
		}
	};
	$scope.delete = function (index){
		
			tableService.deleteTableRow($scope.documentChild.rows[index]).then(
					function (response) {
					},
					function (response) {
						alert("Greska");
					}
				);
	}
	$scope.isDate = function(dateStr) {

    var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var matchArray = dateStr.match(datePat); // is the format ok?

    if (matchArray == null) {
        alert("Unesite datum u formatu mm/dd/yyyy ili mm-dd-yyyy.");
        return false;
    }

    month = matchArray[1]; // p@rse date into variables
    day = matchArray[3];
    year = matchArray[5];

    if (month < 1 || month > 12) { // check month range
        return false;
    }

    if (day < 1 || day > 31) {
        return false;
    }

    if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
        return false;
    }

    if (month == 2) { // check for february 29th
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day == 29 && !isleap)) {
            return false;
        }
    }
    return true; // date is valid
	}

}]);