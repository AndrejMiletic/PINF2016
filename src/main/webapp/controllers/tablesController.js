
app.controller('tablesController', ['$scope', '$window', 'tableService', 'appConstants', function ($scope, $window, tableService, appConstants) {

	function init() {
		tableService.getAll().then(
			function (response) {
				$scope.allTableNames = response.data;
				console.log(response)
			},
			function (response) {
				alert("Greska");
			}
		);
		$scope.modelArray = [];
	};

	init();

	$scope.open = function() {
		$scope.popup1.opened = true;
	}

	$scope.showTable = function () {
        $scope.closeForeignKeyForm();
		$scope.currentRow = undefined;
        if (!$scope.selectedTable) {
            alert("Nije selektovano nista");
        } else {
            $scope.documentChild = {};
            $scope.filteredNextTable = undefined;
            $scope.showNextSelect = false;
            var tableCode;
            angular.forEach($scope.allTableNames, function(value, key){
                if(value == $scope.selectedTable)
                   tableCode = key;
             });
            tableService.getTableByName(tableCode).then(
                function (response) {
                    $scope.requestedTable = response.data;
                    $scope.fieldNames = [];
                    if (response.data.rows[0] !== undefined){
	                    var temp = response.data.rows[0].fields
	                    angular.forEach(temp, function(key, value){
	                    	$scope.fieldNames.push(value);
	                    })
                	}
                    console.log($scope.requestedTable);
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
        }
	};

    $scope.openDocument = function (id) {
        $scope.closeForeignKeyForm();
       /* var childName=$scope.requestedTable.children[0];
        var childCode=tableService.replace(childName);*/
//        if ($scope.requestedTable.documentChildName){
//          tableService.getTableByName(childCode).then(
//              function (response) {
//                  $scope.documentChild = response.data;
//              },
//              function (response) {
//                  alert("Neuspesno dobavljanje tabele");
//              }
//          );
//      }
        if ($scope.requestedTable.documentChildName){
            tableService.getDocChild($scope.requestedTable.tableCode, id).then(
                function (response) {
                    $scope.documentChild = response.data;
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
        }
    };

    $scope.openNextTableTop = function(parentTable, childTable, parentId){
        $scope.closeForeignKeyForm();
    	if ($scope.nextTableTop && $scope.idNextTableTop){
    		tableService.getByNameFiltered(parentTable, childTable, parentId).then(
    			function (response){
    				$scope.filteredNextTable = response.data;
    				document.getElementById('idNext').style.display='block'
    			},
    			function (response){
    				alert("Doslo je do greske");
    			}
    		);
    	}
    }

    $scope.removeTableNext = function(){
        $scope.closeForeignKeyForm();
    	$scope.filteredNextTable = undefined;
    	$scope.currentRow = undefined;
    }

    $scope.showNextTableSelection = function(){
        $scope.closeForeignKeyForm();
    	$scope.showNextSelect = !$scope.showNextSelect;
    }

    $scope.deleteRowNext = function(index, row, event) {
        $scope.closeForeignKeyForm();
		event.stopPropagation();
		tableService.delete($scope.filteredNextTable.tableName, row.fields.id).then(
			function(response) {
					$scope.currentRow = undefined;
					$scope.filteredNextTable.rows.splice(index, 1);
			},
			function(response) {
				alert("Neuspešno brisanje reda");
			}
		);
	}

		$scope.deleteRow = function(index, row, event) {
	        $scope.closeForeignKeyForm();
			event.stopPropagation();
			tableService.delete($scope.requestedTable.tableName, row.fields.Id).then(
				function(response) {
						$scope.documentChild = undefined;
						$scope.currentRow = undefined;
						$scope.requestedTable.rows.splice(index, 1);
				},
				function(response) {
					alert("Neuspešno brisanje reda");
				}
			);
		}

		$scope.deleteSubRow = function(index, row, event) {
	        $scope.closeForeignKeyForm();
			event.stopPropagation();
			tableService.delete($scope.documentChild.tableName, row.fields.Id).then(
				function(response) {
					$scope.currentRow = undefined;
					$scope.documentChild.rows.splice(index, 1);
				},
				function(response) {
					alert("Neuspešno brisanje stavke");
				}
			);
		}

		$scope.generateEditFormNext = function(index, row, event) {
	        $scope.closeForeignKeyForm();
			event.stopPropagation();
			$scope.currentRow = {fields:{}};
			$scope.currentIndex = index;
			$scope.currentRow = angular.copy(row);
			$scope.formText = "Uredi";
			$scope.operation = appConstants.operations.NEXT_EDIT;
			$scope.currentTable = angular.copy($scope.filteredNextTable);
		}

		$scope.generateEditForm = function(index, row, event) {
	        $scope.closeForeignKeyForm();
			event.stopPropagation();
			$scope.currentRow = {fields:{}};
			$scope.currentIndex = index;
			$scope.currentRow = angular.copy(row);
			$scope.formText = "Uredi";
			$scope.operation = appConstants.operations.EDIT;
			$scope.currentTable = angular.copy($scope.requestedTable);
		}

		$scope.generateCreateFormNext = function() {
	        $scope.closeForeignKeyForm();
			$scope.currentRow = {fields:{}};
			$scope.formText = "Dodaj";
			$scope.operation = appConstants.operations.NEXT_CREATE;
			$scope.currentTable = angular.copy($scope.filteredNextTable);
		}

		$scope.generateCreateForm = function() {
	        $scope.closeForeignKeyForm();
			$scope.currentRow = {fields:{}};
			$scope.formText = "Dodaj";
			$scope.operation = appConstants.operations.CREATE;
			$scope.currentTable = angular.copy($scope.requestedTable);
		}

		$scope.generateEditSubForm = function(index, row, event) {
	        $scope.closeForeignKeyForm();
			event.stopPropagation();
			$scope.currentRow = {fields:{}};
			$scope.currentIndex = index;
			$scope.currentRow = angular.copy(row);
			$scope.formText = "Uredi stavku";
			$scope.operation = appConstants.operations.SUB_EDIT;
			$scope.currentTable = angular.copy($scope.documentChild);
		}

		$scope.generateCreateSubForm = function() {
	        $scope.closeForeignKeyForm();
			$scope.currentRow = {fields:{}};
			$scope.formText = "Dodaj stavku";
			$scope.operation = appConstants.operations.SUB_CREATE;
			$scope.currentTable = angular.copy($scope.documentChild);
		}

		$scope.filterForm = function(){
			$scope.closeForeignKeyForm();
			$scope.currentRow = {fields:{}};
			$scope.formText = "Filtriraj";
			$scope.operation = appConstants.operations.FILTER;
			$scope.currentTable = angular.copy($scope.requestedTable);
		}

		$scope.submitFilterForm = function(){
			$scope.closeForeignKeyForm();
			var row =  angular.copy($scope.currentRow);

			for(var field in $scope.currentTable.fields){
				if($scope.currentTable.fields[field].type=='DATE'){
					var name=$scope.currentTable.fields[field].name;
					var dateField=$scope.currentRow.fields[name];
					if(dateField!=null){
						var date=dateField.toLocaleDateString("sr-rs");
						row.fields[name]=date;
					}
				}
			}

			if(tableService.isValidFilter($scope.currentTable, row)) {
				tableService.filter($scope.requestedTable.tableName, row).then(
						function(response){
							$scope.requestedTable = response.data;
						},
						function(response){
							alert("Nema rezultata");
						}
				);
			}else{
				alert("Forma nije validna, tabela je neizmenjena");
			}
		}

		$scope.submitForm = function() {
	        $scope.closeForeignKeyForm();

			if($scope.operation === appConstants.operations.CREATE || $scope.operation === appConstants.operations.SUB_CREATE || $scope.operation === appConstants.operations.NEXT_CREATE){
				$scope.currentRow.fields.Id = $scope.getMaxId();
			}

			var row =  angular.copy($scope.currentRow);

			for(var field in $scope.currentTable.fields){
				if($scope.currentTable.fields[field].type=='DATE'){
					var name=$scope.currentTable.fields[field].name;
					var dateField=$scope.currentRow.fields[name];
					if(dateField!=null){
						var date=dateField.toLocaleDateString("sr-rs");
						row.fields[name]=date;
					}
				}
			}

			if(tableService.isValid($scope.currentTable, row)) {
				if($scope.operation === appConstants.operations.CREATE) {
					tableService.create($scope.requestedTable.tableName, row).then(
						function(response) {
							$scope.requestedTable.rows.push(row);
							$scope.currentRow = undefined;
							if($scope.operation === appConstants.operations.CREATE) {
								//$scope.showTable();
							}else if($scope.operation === appConstants.operations.SUB_CREATE) {
								//$scope.openDocument();
							}
						}, function() {
							alert("Neuspešno dodavanje reda u tabelu.");
						}
					);
				} else
				if($scope.operation === appConstants.operations.EDIT) {
					tableService.edit($scope.requestedTable.tableName, row).then(
						function(response) {
							$scope.requestedTable.rows[$scope.currentIndex] = row;
							$scope.currentRow = undefined;
						}, function() {
							alert("Neuspešno uređivanje reda u tabeli.");
						}
					);
				} else
				if($scope.operation === appConstants.operations.SUB_EDIT) {
					console.log($scope.documentChild.tableName);
					console.log(row);
					tableService.edit($scope.documentChild.tableName, row).then(
						function(response) {
							$scope.documentChild.rows[$scope.currentIndex] = row;
							$scope.currentRow = undefined;
						}, function() {
							alert("Neuspešno uređivanje stavke u tabeli.");
						}
					);
				} else
				if($scope.operation === appConstants.operations.SUB_CREATE) {
					tableService.create($scope.documentChild.tableName, row).then(
						function(response) {
							$scope.documentChild.rows.push(row);
							$scope.currentRow = undefined;
						}, function() {
							alert("Neuspešno dodavanje stavke.");
						}
					);
				} else
					if($scope.operation === appConstants.operations.NEXT_EDIT) {
						tableService.edit($scope.filteredNextTable.tableName, row).then(
							function(response) {
								$scope.filteredNextTable.rows[$scope.currentIndex] = row;
								$scope.currentRow = undefined;
							}, function() {
								alert("Neuspešno uređivanje stavke u tabeli.");
							}
						);
				} else
					if($scope.operation === appConstants.operations.NEXT_CREATE) {
						tableService.create($scope.filteredNextTable.tableName, row).then(
							function(response) {
								$scope.filteredNextTable.rows.push(row);
								$scope.currentRow = undefined;
							}, function() {
								alert("Neuspešno dodavanje stavke.");
							}
						);
					}
			} else {
				alert("Forma nije validna!");
			}
		}

		$scope.getMaxId=function(){
			if($scope.currentTable.rows.length > 0) {
				return ($scope.currentTable.rows[$scope.currentTable.rows.length-1].fields.Id)+1;
			} else {
				return 1;
			}
		}

		$scope.closeForm = function() {
		        $scope.closeForeignKeyForm();
				$scope.currentRow = undefined;
		}

		$scope.foreignKey = function(field) {
//            $scope.documentChild = {};
            var code=tableService.replace(field.fkTableName);

            tableService.getTableByName(code).then(
                function (response) {
                	$scope.foreignTable = response.data;
			        if($scope.foreignTable){
						$scope.foreignKeyClicked=true;
						$scope.foreignTableName=field.fkTableName;
						$scope.foreignKeyField=field;

		            }else{
		            	alert("Ne postoji nijedna tabela.");
		            }
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
	}

	$scope.openDocumentForeignKey=function(row){
		$scope.selectedForeignKey=row.fields.Id;
		if($scope.currentTable.tableCode!="Poslovni_partner"){
			for(var field in $scope.currentTable.fields){
				if($scope.currentTable.fields[field].lookup){
					var fkTableName=$scope.currentTable.fields[field].fkTableName;
					if(fkTableName==$scope.foreignKeyField.name){
						var name=$scope.currentTable.fields[field].name;
						$scope.selectedForeignKeyName=row.fields[name];
					}
				}
			}
		}else{
			if($scope.foreignKeyField.name=="Partner"){
				$scope.requestedTable.rows[0].fields["Naziv partnera"]=row.fields["Naziv preduzeća"]
				$scope.selectedForeignKeyName=row.fields["Naziv preduzeća"];
			}else if($scope.foreignKeyField.name=="Preduzeće"){
				$scope.requestedTable.rows[0].fields["Naziv preduzeća"]=row.fields["Naziv preduzeća"]
				$scope.requestedTable.rows[0].fields["Naziv partnera"]=row.fields[""]
				$scope.selectedForeignKeyName=row.fields["Naziv preduzeća"];
			}
		}
        if ($scope.foreignTable && $scope.foreignTable.documentPattern && $scope.foreignTable.documentChildName){
            tableService.getDocChild($scope.foreignTable.tableCode, row.fields.Id).then(
                function (response) {
                    $scope.documentForeignChild = response.data;
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
        }
	}

	$scope.addForeignKey=function(){
		if($scope.selectedForeignKey){
			$scope.currentRow.fields[$scope.foreignKeyField.name]=$scope.selectedForeignKey;
			if($scope.requestedTable.tableCode=="Poslovni_partner"){
				if($scope.currentRow.fields["Partner"]){
					$scope.currentRow.fields["Naziv partnera"]=$scope.selectedForeignKeyName;
				}else if($scope.currentRow.fields["Preduzeće"]){
					$scope.currentRow.fields["Naziv preduzeća"]=$scope.selectedForeignKeyName;
					$scope.requestedTable.rows[0].fields["Naziv partnera"]=$scope.selectedForeignKeyName;
				}
				console.log($scope.currentRow);
			}else{
				for(var field in $scope.currentTable.fields){
					if($scope.currentTable.fields[field].lookup){
						var fkTableName=$scope.currentTable.fields[field].fkTableName;
						if(fkTableName==$scope.foreignKeyField.name){
							var name=$scope.currentTable.fields[field].name;
							$scope.currentRow.fields[name]=$scope.selectedForeignKeyName;
						}
					}
				}
			}
			$scope.foreignKeyClicked=false;
			$scope.selectedForeignKey=null;
			$scope.selectedForeignKeyName=null;
		}else{
			alert("Niste izabrali tabelu.");
		}

	}

	$scope.closeForeignKeyForm=function(){
		$scope.foreignKeyClicked=false;
		$scope.selectedForeignKey=null;
		$scope.selectedForeignKeyName=null;
	}

		$scope.downloadPDF = function(row, $event) {
	        $scope.closeForeignKeyForm();
			$event.stopPropagation();
			tableService.generatePDF(row.fields.Id).then(
				function(response) {
					$scope.genericDownload("faktura.pdf");
				}
			);
		}

		$scope.downloadXML = function(row, $event) {
	        $scope.closeForeignKeyForm();
			$event.stopPropagation();
			tableService.generateXML(row.fields.Id).then(
				function(response) {
					$scope.genericDownload("faktura.xml");
				}
			);
		}

		$scope.downloadKIF = function() {
	        $scope.closeForeignKeyForm();
			if(tableService.isKIFFormValid($scope.dateFrom, $scope.dateTo)) {
				tableService.generateKIF($scope.dateFrom, $scope.dateTo).then(
					function(response) {
						$scope.genericDownload("kif.pdf");
					}
				);
			} else {
				alert("Forma nije validna");
			}
		}

		$scope.genericDownload = function(fileName) {
		var name = "", path = "downloads/" + fileName;

		if(fileName) {
			name = fileName;
		}
		var downloadLink = angular.element('<a></a>');
		downloadLink.attr('href', path);
		downloadLink.attr('download', name);
		downloadLink[0].click();
	}

		$scope.setSelectedPricelist = function(row){
			$scope.selectedPricelistRow = row;
			$scope.selectedPricelist = row.fields["Naziv cenovnika"];
			console.log(row)
			$scope.selectedDataPricelist = row;
			$scope.openDocumentPriceList();
		}

		$scope.openDocumentPriceList = function () {


			 if($scope.selectedPricelist){

				 tableService.getDocChild($scope.requestedTable.tableName, $scope.selectedPricelistRow.fields.Id).then(
					function (response) {
						$scope.documentChild = response.data;
						if($scope.documentChild.rows.length != 0){
								$scope.parentID = $scope.documentChild.rows[0].fields.cenovnik;
								$scope.date =  $scope.selectedDataPricelist.fields["Datum primene"];
								console.log($scope.selectedDataPricelist.fields["Datum primene"])
						}
					},
					function (response) {
						alert("Neuspesno dobavljanje tabele");
					}
				);


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
			console.log($scope.priceListNaziv)
			if (!$scope.priceListNaziv){
				alert("Unesite naziv cenovnika");
			}
			else if($scope.selectedDataPricelist.fields["Datum primene"] == $scope.date){
				alert("Promeni datum primene cenovnika.");
			}else{
				$scope.selectedDataPricelist.fields["Datum primene"] = $scope.selectedDataPricelist.fields["Datum primene"].toLocaleDateString("sr-rs");
				$scope.selectedDataPricelist.fields["Naziv cenovnika"] = $scope.priceListNaziv;
					var pricelist = {
						parent :  $scope.selectedDataPricelist,
						child : $scope.documentChild.rows
					}

					tableService.addPricelist(pricelist).then(
							function (response) {
								 tableService.getTableByName("Cenovnik").then(
									function (response) {
										alert("Uspešno kopiran cenovnik");
										$scope.requestedTable = response.data;
										document.getElementById('idCenovnik').style.display='none';
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
