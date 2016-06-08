app.service('tableService', function($http){
	var url = '/table';

	this.getAll = function(){
		return $http.get(url + "/getAll");
	}
	this.getTableByName = function(name) {
		return $http.get(url + "/getByName/" + name);
	}
    
    this.getDocChild = function(parentName, parentId){
        return $http.get(url + "/getDocChild/" + parentName + "/" + parentId);
    }
	this.addPricelist = function(pricelist){
        return $http.post(url + "/addPricelist", pricelist);
    }
});
