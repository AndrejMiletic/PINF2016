var app = angular.module('app', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
			.when('/tables', {
						controller: 'tablesController',
						templateUrl: 'partials/tablesPartial.html',
			})
			.when('/copyPricelist', {
						controller: 'copyPricelistController',
						templateUrl: 'partials/copyPricelist.html',
					})
			.when('/invoicing', {
						controller: 'invoicingController',
						templateUrl: 'partials/invoicingPartial.html',
					})
			.when('/invoiceExport', {
						controller: 'invoiceController',
						templateUrl: 'partials/invoicePartial.html',
					})
	    .otherwise({
	        redirectTo: '/tables'
	    });
}]);
