app.service('tableService', ['$http', 'appConstants', function($http, appConstants){
	var url = '/table';

	this.filter = function(table, entity){
		var code = this.replace(table);
		var payload = {
			tableName: table,
			tableCode: code,
			fields: entity.fields
		}
		return $http.post(url + "/filter", payload);
	}

	this.getAll = function(){
		return $http.get(url + "/getAllNames");
	}

	this.getTax=function(tableName,id){
		var tableCode=this.replace(tableName);
		return $http.get(url + "/getTax/" + tableCode + "/" + id);
	}

	this.getCalculatedData=function(id,data,itemId){
		return $http.get(url + "/getCalculatedData/" + id + "/" + itemId + "/"+ data);
	}


	this.getTableByName = function(tableCode) {
		return $http.get(url + "/getAll/" + tableCode);
	}

	this.getAllOrders=function(){
		return $http.get(url + "/getAllOrders");
	}

	this.getMaxId=function(tableName){
		var tableCode=this.replace(tableName);
		return $http.get(url+ "/maxId/"+tableCode);
	}

	this.getTableById=function(tableName,Id){
		var tableCode=this.replace(tableName);
		return $http.get(url + "/getById/" + tableCode + "/" + Id);
	}

	this.create = function(parent, entity) {
		 var newItems = {};
		    angular.forEach(entity.fields, function(value, key){
		        if(key != "Id")
		            newItems[key] = value;
		    });

		var code=this.replace(parent);
		var payload = {
			tableName: parent,
			tableCode: code,
			fields: newItems
		}
		return $http.post(url + "/create", payload);
	}

	function removeId(items,item) {
	    var newItems = {};
	    angular.forEach(items, function(value, key){
	        if(key != item)
	            newItems[key] = value;
	    });

	    return newItems;
	};

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

	this.isValidFilter = function(table, row){
		var currentValue;
		var isValid = true;

		if(!row.fields) {
			isValid = false;
		} else
		{
			angular.forEach(table.fields, function(field, key) {
				currentValue = row.fields[field.name];
				if(field.name!=="Id") {

					if(field.type === appConstants.types.NUMBER) {

						if(!angular.isNumber(currentValue) && parseInt(currentValue) === NaN) {
							isValid = false;
						}
					}
				}
			});
			return isValid;
		}
	}

	this.isValid = function(table, row) {

		var validMessage="";
		var currentValue;
		var isValid = true;

		if(!row.fields) {
			isValid = false;
			validMessage+="Forma ne sadrži polja.\n";
		} else
		{
			angular.forEach(table.fields, function(field, key) {
				currentValue = row.fields[field.name];
				isValid=true;
				if(field.name!=="Id" && !field.calculated) {
					if(!field.nullable && field.regExp=="" && field.type!="BOOLEAN") {
						if(!currentValue && !field.calculated) {
							isValid = false;
							validMessage+="Polje: '" + field.name +"' mora da bude uneseno.\n";
						}
					}
					if(field.type === appConstants.types.NUMBER && isValid) {
						if(!currentValue && !field.calculated) {
							if(field.regExp!=""){
								isValid=false;
								var somthing=convertRegExp(field.regExp);
								validMessage+="Polje: '" + field.name + "' mora da bude u formatu : " + field.regExp + ".\n";
							}
						} else
						if(isNaN(currentValue)) {
							isValid = false;
							validMessage+="Polje: '" + field.name + "' mora da bude broj.\n";
						}
					} else
					if(field.type === appConstants.types.DATE && isValid) {
						if(!currentValue) {
						}
					} else
					if(field.type === appConstants.types.TEXT  && isValid) {
						if(currentValue){
							currentValue = currentValue.trim();
							if(currentValue.length === 0) {
								isValid = false;
								validMessage+="Polje: '" + field.name + "' mora da bude uneseno.\n";
							}
						}else
							if(field.regExp!=""){
								isValid=false;
								var somthing=convertRegExp(field.regExp);
								validMessage+="Polje: '" + field.name + "' mora da bude u formatu : " + field.regExp + ".\n";
							}
					}else
					if(field.type=='CHAR' && isValid){
						if(currentValue) {
							var maxLength=field.maxLength;
							if(maxLength!=-1){
								if(currentValue.length!=maxLength){
									isValid=false;
									validMessage+="Polje: '" + field.name + "' mora da sadrži tačno " + maxLength + " karaktera.\n";
								}
							}
						}
					}
				}
			});
		}
		if(validMessage.length>0){
			alert(validMessage);
			isValid=false;
		}
		return isValid;
	}

	getMax=function(regExp){
		var number="";
		for(var c in regExp){
			if(regExp[c]=="1" || regExp[c]=="1" || regExp[c]=="2" || regExp[c]=="3" || regExp[c]=="4"
				|| regExp[c]=="5" || regExp[c]=="6" || regExp[c]=="7" || regExp[c]=="8" || regExp[c]=="9"){
				number+=regExp[c];
			}
		}
		if(number.length>0)
			return parseInt(number);
		else
			return 0;
	}

	convertRegExp=function(regExp){
		var open=false;
		var digit="";
		var number="";
		var numberTo="";
		var to=false;
		var message="";
		for(var c in regExp){
			if(regExp[c]=="\\"){
				digit="";
			}else if(regExp[c]=="d"){
				digit="b";
			}else if(regExp[c]=="1" || regExp[c]=="2" || regExp[c]=="3" || regExp[c]=="4"
					|| regExp[c]=="5" || regExp[c]=="6" || regExp[c]=="7" || regExp[c]=="8" || regExp[c]=="9"){
				if(!to)
					number+=regExp[c];
				else
					numberTo+=regExp[c];
			}else if(regExp[c]=="-"){
				message+="-";
			}else if(regExp[c]=="{"){
				open=true;
			}else if(regExp[c]=="}"){
				open=false;
				if(!to){
					var num=parseInt(number);
					for(var n=0;n<num;n++){
						message+=digit;
					}
				}else{
					message+="("+number+"-"+numberTo+")b";
				}
				to=false;
				numberTo="";
				number="";
			}else if(regExp[c]==",")
				to=true;
			else if(regExp[c]=="("){
				number=0;
				to=true;
			}else if(regExp[c]==".")
				message+=".";
		}
		console.log(message);
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
