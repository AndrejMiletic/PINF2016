var app = angular.module('app', ['ngRoute','ui.bootstrap']);

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
			.when('/tables', {
						controller: 'tablesController',
						templateUrl: 'partials/tablesPartial.html',
			})
			.when('/copyPricelist', {
						controller: 'copyPricelistController',
						templateUrl: 'partials/copyPricelistPartial.html',
					})
			.when('/invoicing', {
						controller: 'invoicingController',
						templateUrl: 'partials/invoicingPartial.html',
					})
			.when('/KIF', {
						controller: 'tablesController',
						templateUrl: 'partials/KIFPartial.html',
					})
	    .otherwise({
	        redirectTo: '/tables'
	    });
}]);

app.directive('datePicker', function() {
	var directive = {};
    directive.restrict = 'E';
    directive.templateUrl = "templates/datePickerTemplate.html";
		directive.link = function($scope, element, attrs) {
			$scope.isOpened = false;
			$scope.open = function() { $scope.isOpened = true;}
		}
		directive.scope =  {
        model:'=ngModel',	minDate: '=min'
    }

    return directive;
});