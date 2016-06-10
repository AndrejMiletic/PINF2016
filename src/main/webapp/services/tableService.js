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

  	this.getByNameFiltered = function(parentTable, childTable, parentId){
  		return $http.get(url + "/filterNextTable/" + childTable + "/" + parentTable + "/" + parentId);
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

	this.generatePDF = function(id) {
		return $http.get(url + "/generatePDF/" + id);
	}

	this.generateXML = function(id) {
		return $http.get(url + "/generateXML/" + id);
	}

	this.generateKIF = function(dateFrom, dateTo) {
		var payload = {
			dateFrom : new Date(dateFrom),
			dateTo : new Date(dateTo)
		}
		return $http.post(url + "/generateKIF", payload);
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

	this.isKIFFormValid = function(dateFrom, dateTo) {
		var d1, d2;
		if(dateFrom && dateTo && isDate(dateFrom) && isDate(dateTo)) {
			d1= new Date(dateFrom);
			d2= new Date(dateTo);
			if(d1 < d2) {
				return true;
			}
		}
		return false;
	}

	this.isValidPricelistDate = function(date){
		if(date){
			if(isDate(date)){
				return true;
			}else{
				alert("Unesite datum veći od današnjeg u formatu mm/dd/yyyy.");
				return false;
			}
			
		}
	};
	
	isDate = function(dateStr) {

    var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var matchArray = dateStr.match(datePat); // is the format ok?

    if (matchArray == null) {
        // alert("Unesite datum u formatu mm/dd/yyyy ili mm-dd-yyyy.");
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
	if(day < 10 || month < 6 || year < 2016){
		return false;
	}
    return true; // date is valid
	}
	
	this.getTableRows=function(tableName){
		return $http.get(url+"/getTableRows/"+tableName);
	}
	
	this.addTableRow=function(tableName, rows){
		return $http.post(url+"/addRow/"+tableName,rows);
	}
	
	
}]);
