app.service('tableService', ['$http', 'appConstants', function($http, appConstants){
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

	this.create = function(parent, entity) {		
		var payload = {
			tableName: parent,
			fields: entity
		}
		return $http.post(url + "/addRow", payload);
	}
	
	this.edit = function(parent, entity) {
		var payload = {
			tableName: parent,
			fields: entity
		}
		return $http.patch(url + "/editRow", payload);
	}

	this.delete = function(tableName, id) {
		return $http.delete(url + "/" + tableName + "/" + id);
	}
	
	this.addPricelist = function(pricelist){
        return $http.post(url + "/addPricelist", pricelist);
    }
	this.addTableRow = function(row){
        return $http.post(url + "/addTableRow", row);
    }
	this.deleteTableRow = function(row){
        return $http.post(url + "/deleteTableRow", row);
    }
	
	this.isValid = function(table, row) {
		var currentValue;
		var isValid = true;

		if(!row.fields) {
			isValid = false;
		} else
		{
			angular.forEach(table.fields, function(field, key) {
				currentValue = row.fields[field.name];
				if(field.name!=="id") {

					if(!field.nullable) {
						if(!currentValue) {
							isValid = false;
						}
					}
					if(field.type === appConstants.types.NUMBER) {
						if(!currentValue) {
							isValid = false;
						} else
						if(!angular.isNumber(currentValue) && parseInt(currentValue) === NaN) {
							isValid = false;
						}
					} else
					if(field.type === appConstants.types.DATE) {
						if(!currentValue) {
							isValid = false;
						} else
						if(!isDate(currentValue)) {
							isValid =  false;
						}
					} else
					if(field.type === appConstants.types.TEXT ) {
						if(!currentValue) {
							isValid = false;
						} else {
							currentValue = currentValue.trim();
							if(currentValue.length === 0) {
								isValid = false;
							}
						}
					}
				}
			});
		}
		return isValid;
	}

	isDate = function(dateStr) {

    var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var matchArray = dateStr.match(datePat); // is the format ok?

    if (matchArray == null) {
        alert("Unesite datum u formatu mm/dd/yyyy ili mm-dd-yyyy.");
        return false;
    }

    month = matchArray[1]; // p@rse date into variables
    day = matchArray[3];
    year = matchArray[5];

    if (month < 1 || month > 12) { // check month range
        return false;
    }

    if (day < 1 || day > 31) {
        return false;
    }

    if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
        return false;
    }

    if (month == 2) { // check for february 29th
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day == 29 && !isleap)) {
            return false;
        }
    }
    return true; // date is valid
	}
}]);
