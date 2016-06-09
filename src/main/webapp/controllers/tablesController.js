
app.controller('tablesController', ['$scope', '$window', 'tableService', 'appConstants', function ($scope, $window, tableService, appConstants) {

	function init() {
		tableService.getAll().then(
			function (response) {
				$scope.allTableNames = response.data;
			},
			function (response) {
				alert("Greska");
			}
		);
	};

	init();

	$scope.showTable = function () {
		$scope.currentRow = undefined;
        if (!$scope.selectedTable) {
            alert("Nije selektovano nista");
        } else {
            $scope.documentChild = {};
            tableService.getTableByName($scope.selectedTable).then(
                function (response) {
                    $scope.requestedTable = response.data;
                    $scope.fieldNames = [];
                    var temp = response.data.rows[0].fields
                    angular.forEach(temp, function(key, value){
                    	$scope.fieldNames.push(value);
                    })
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
        }
	};

    $scope.openDocument = function (id) {
        if ($scope.requestedTable.documentChildName){
            tableService.getDocChild($scope.requestedTable.tableName, id).then(
                function (response) {
                    $scope.documentChild = response.data;
                },
                function (response) {
                    alert("Neuspesno dobavljanje tabele");
                }
            );
        }
    };

		$scope.deleteRow = function(index, row, event) {
			event.stopPropagation();
			tableService.delete($scope.requestedTable.tableName, row.fields.id).then(
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
			event.stopPropagation();
			tableService.delete($scope.documentChild.tableName, row.fields.id).then(
				function(response) {
					$scope.currentRow = undefined;
					$scope.documentChild.rows.splice(index, 1);
				},
				function(response) {
					alert("Neuspešno brisanje stavke");
				}
			);
		}

		$scope.generateEditForm = function(index, row, event) {
			event.stopPropagation();
			$scope.currentRow = {fields:{}};
			$scope.currentIndex = index;
			$scope.currentRow = angular.copy(row);
			$scope.formText = "Uredi";
			$scope.operation = appConstants.operations.EDIT;
			$scope.currentTable = angular.copy($scope.requestedTable);
		}

		$scope.generateCreateForm = function() {
			$scope.currentRow = {fields:{}};
			$scope.formText = "Dodaj";
			$scope.operation = appConstants.operations.CREATE;
			$scope.currentTable = angular.copy($scope.requestedTable);
		}

		$scope.generateEditSubForm = function(index, row, event) {
			event.stopPropagation();
			$scope.currentRow = {fields:{}};
			$scope.currentIndex = index;
			$scope.currentRow = angular.copy(row);
			$scope.formText = "Uredi stavku";
			$scope.operation = appConstants.operations.SUB_EDIT;
			$scope.currentTable = angular.copy($scope.documentChild);
		}

		$scope.generateCreateSubForm = function() {
			$scope.currentRow = {fields:{}};
			$scope.formText = "Dodaj stavku";
			$scope.operation = appConstants.operations.SUB_CREATE;
			$scope.currentTable = angular.copy($scope.documentChild);
		}

		$scope.submitForm = function() {
			$scope.currentRow.fields.id = 100;
			var row =  angular.copy($scope.currentRow);

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
				}
			} else {
				alert("Forma nije validna!");
			}
		}

		$scope.closeForm = function() {
				$scope.currentRow = undefined;
		}

		$scope.foreignKey = function(field) {
			alert("Preuzeti FK vrednost za polje ispisano u konzoli!");
			console.log(field);
			$scope.currentRow.fields[field.name] = "10-10-2015";
		}
}]);
