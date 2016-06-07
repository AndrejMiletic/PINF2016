app.service('invoiceService',function($http){
	
	var url="/table";
	
	this.getAllOrderForms=function(){
		return $http.get(url+"/getAll");
	}
	
	this.getByName=function(name){
		return $http.get(url+"/getByName/"+name);
	}
	
	this.addOrderForm=function(orderForm){
		return $http.post(url+"/add", orderForm);
	}
	
});