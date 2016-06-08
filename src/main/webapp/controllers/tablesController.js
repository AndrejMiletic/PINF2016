
app.controller('tablesController', ['$scope', '$window', 'tableService', function ($scope, $window, tableService) {

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
                    console.log($scope.fieldNames);
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
    
}]);