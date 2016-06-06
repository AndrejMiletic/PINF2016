app.controller('invoicingController',['$scope','invoiceService',function($scope,invoiceService){
	
	function init(){
		invoiceService.getAllOrderForms().then(
			function(response){
				$scope.allOrderForms=response.data;
			}
		);
	}
	
	init();

	$scope.showOrderForm=function(){
		if($scope.selectedOrderForm){
			$scope.showTable=true;
			invoiceService.getByName($scope.selectedOrderForm).then(
			function(response) {
				$scope.orderForm = response.data;
			},
			function(response){
				alert("Doslo je do greske.")
			}
			);
		
		}
	}
	
	$scope.createOrderForm=function(){
		if($scope.orderForm){
			invoiceService.addOrderForm($scope.orderForm).then(
				function(response){
					alert("Uspesno kreirana faktura.");
				},
				function(response){
					alert("Doslo je do greske prilikom kreiranja fakture.");
				}
			);
		}
	}
	
}]);