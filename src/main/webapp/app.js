var app = angular.module('app', ['ngRoute']);

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
