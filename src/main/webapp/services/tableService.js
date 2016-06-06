app.service('tableService', function($http){
	var url = '/table';
	this.getAll = function(user){
		return $http.get(url + "/getAll");
	}
	
	this.login = function(user){
		return $http.post(url + "/login", user);
	}

	this.getTableByName = function(name) {
		return $http.get(url + "/getByName/" + name);
	}
});
