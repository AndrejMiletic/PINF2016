
app.controller('tablesController', ['$scope', '$window', 'tableService', function($scope, $window, tableService){

	function init(){
		tableService.getAll().then(
			function(response){
				$scope.allTableNames = response.data;
			},
			function(response){
				alert("Greska");
			}
		);
	}

	init();

	$scope.setTable = function(table){
		$scope.current = table;
		alert(table);
	}
	
}]);