app.service('tableService', ['$http', 'appConstants', function($http, appConstants){
	var url = '/table';

	this.getAll = function(){
		return $http.get(url + "/getAllNames");
	}
	this.getTableByName = function(tableCode) {
		return $http.get(url + "/getAll/" + tableCode);
	}

	this.getTableById=function(tableName,Id){
		var tableCode=this.replace(tableName);
		return $http.get(url + "/getById/" + tableCode + "/" + Id);
	}

	this.create = function(parent, entity) {
		var code=this.replace(parent);
		var payload = {
			tableName: parent,
			tableCode: code,
			fields: entity.fields
		}
		return $http.post(url + "/create", payload);
	}

	this.edit = function(parent, entity) {
		var code=this.replace(parent);

		var payload = {
			tableName: parent,
			tableCode: code,
			fields: entity.fields
		}
		return $http.put(url + "/update", payload);
	}

	this.delete = function(tableName, id) {
		var code=this.replace(tableName);

		return $http.delete(url + "/delete/" + code + "/" + id);
	}

	this.replace=function(tableName){
		var tableCode;
		tableCode = tableName.replace(" ", "_")
					.replace("ć", "c")
					.replace("ž", "z")
					.replace("š", "s");

		tableCode=tableCode.replace(" ", "_")
					.replace("ć", "c")
					.replace("ž", "z")
					.replace("š", "s");

		tableCode=tableCode.replace(" ", "_")
					.replace("ć", "c")
					.replace("ž", "z")
					.replace("š", "s");

		tableCode=tableCode.replace(" ", "_")
					.replace("ć", "c")
					.replace("ž", "z")
					.replace("š", "s");
		return tableCode;
	}

    this.getDocChild = function(parentName, parentId){
        return $http.get(url + "/getDocChild/" + parentName + "/" + parentId);
    }

  	this.getByNameFiltered = function(parentTable, childTable, parentId){

  		var childTableCode= this.replace(childTable);
  		var parentTableCode= this.replace(parentTable);

  		return $http.get(url + "/filterNextTable/" + childTableCode + "/" + parentTableCode + "/" + parentId);
  	}

	this.generatePDF = function(id) {
		return $http.get(url + "/generatePDF/" + id);
	}

	this.generateXML = function(id) {
		return $http.get(url + "/generateXML/" + id);
	}

	this.generateKIF = function(dateFrom, dateTo, company) {
		var payload = {
			dateFrom : dateFrom.toLocaleDateString("sr-rs"),
			dateTo : dateTo.toLocaleDateString("sr-rs"),
			companyId : company.fields.Id,
			companyName : company.fields["Naziv preduzeća"]
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

		console.log(table);
		console.log(row);
		var currentValue;
		var isValid = true;

		if(!row.fields) {
			isValid = false;
		} else
		{
			angular.forEach(table.fields, function(field, key) {
				currentValue = row.fields[field.name];
				if(field.name!=="Id") {
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
						}
//						else
//						if(!isDate(currentValue)) {
//							isValid =  false;
//						}
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
					}else
					if(field.type=='CHAR'){
						var maxLength=field.maxLength;
						if(maxLength!=-1){
							if(currentValue.length!=maxLength){
								isValid=false;
							}
						}
					}
				}
			});
		}
		return isValid;
	}

	this.isKIFFormValid = function(dateFrom, dateTo, company) {
		if(company && dateFrom && dateTo && dateFrom < dateTo) {
				return true;
		}
		return false;
	}

	this.isValidPricelistDate = function(date){
		if(date){
			if(isDate(date)){
				var newDate = new Date(date);
				var todayDate = new Date();
				if(newDate < todayDate){
					alert("Unesite datum veći od današnjeg.");
					return false;
				}
				return true;
			}else{
				alert("Unesite datum u formatu mm/dd/yyyy.");
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
	    return true; // date is valid
	}

	this.getTableRows=function(tableName){
		return $http.get(url+"/getTableRows/"+tableName);
	}

	this.addTableRow=function(tableName, rows){
		return $http.post(url+"/addRow/"+tableName,rows);
	}

	this.getCompaniesForKIF = function() {
		return $http.get(url+"/companiesForKIF");
	}

}]);
