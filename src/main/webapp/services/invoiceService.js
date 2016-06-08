app.service('invoiceService',function($http){
	
	var url="/table";
	
	this.getTableRows=function(tableName){
		return $http.get(url+"/getTableRows/"+tableName);
	}
	
	this.getByName=function(tableName){
		return $http.get(url+"/getTable/"+tableName);
	}
	
	this.addTableRow=function(tableName, rows){
		return $http.post(url+"/addRow/"+tableName,rows);
	}
	
});