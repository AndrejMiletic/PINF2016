app.controller('copyPricelistController', ['$scope', '$window', function($scope, $window){
		function init(){
		tableService.getAllPricelistTables().then(
			function(response){
				$scope.pricelistTables = response.data;
			},
			function(response){
				alert("Greska");
			}
		);
	}

	init();

	$scope.showTable = function(){
	}
}]);