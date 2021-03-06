app.controller('invoicingController',['$scope','tableService','$timeout',function($scope, tableService,$timeout){

	function init(){
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		$scope.currentDate=dd+"."+mm+"."+yyyy+".";

		$scope.addedInvoice=[];

		tableService.getAllOrders().then(
				function(response){
					$scope.allOrderForms=response.data;
				}
		);

//		tableService.getTableByName("Narudzba").then(
//				function(response){
//					$scope.allOrderForms=[];
//					$scope.naruzdba=response.data
//					for(var rowIndex in response.data.rows){
//						var Id=response.data.rows[rowIndex].fields.Id;
//				        $scope.allOrderForms.push(Id);
//					}
//				}
//		);

		$scope.itemsIdMap={};
		$scope.rabatMap={};
		$scope.ukupanIznos=0;
		$scope.ukupanRabat=0;
		$scope.cenaSaRabatom=0;
		$scope.ukupanPDV=0;
		$scope.ukupnoZaNaplatu=0;
		$scope.additionalNotes="";
	}

	init();

	$scope.showOrderForm=function(){
		$scope.additionalNotes="";

		$scope.ukupanIznos=0;
		$scope.ukupanRabat=0;
		$scope.cenaSaRabatom=0;
		$scope.ukupanPDV=0;
		$scope.ukupnoZaNaplatu=0;
		if($scope.selectedOrderForm){
			$scope.showTable=true;
			tableService.getTableByName('Narudzba').then(
				function(response) {
					$scope.orderForm= response.data;
					$scope.createInvoice();
					tableService.getTableByName('Stavke_narudzbe').then(
						function(response){
							$scope.orderFormItems=response.data;
							$scope.createInvoiceItems();
						},
						function(response){
							alert("Doslo je do greske prilikom preuzimanja tabele 'Stavka narudzbe'.")
						}
					);
				},
				function(response){
					alert("Doslo je do greske prilikom preuzimanja tabele 'Narudzba'.");
				}
			);
		}else
			alert("Niste odabrali narudzbenicu.");
	}

	$scope.createInvoice=function(){
		var tableName="Faktura i otpremnica";
		tableService.getTableByName('Faktura_i_otpremnica').then(
			function(response) {
				$scope.invoice= response.data;
				var fields = {};
				var rowFieldsFromOrderForm={};
				for(var row in $scope.orderForm.rows){
					var id=$scope.orderForm.rows[row].fields.Id;
					if(id==$scope.selectedOrderForm){
						rowFieldsFromOrderForm=$scope.orderForm.rows[row].fields;
						break;
					}
				}
				fields["Id"]=0;
				tableService.getMaxId(tableName).then(
						function(response){
							fields["Id"]=response.data;
						}
				);
				$scope.ukupanIznos=0;
				$scope.ukupanRabat=0;
				$scope.cenaSaRabatom=0;
				$scope.ukupanPDV=0;
				$scope.ukupnoZaNaplatu=0;
				fields["Broj fakture/otpremnice"]=rowFieldsFromOrderForm["Broj narudžbe"];
				fields["Tip"]="R";
				fields["Datum fakture"]=rowFieldsFromOrderForm["Datum naručivanja"];
				fields["Datum valute"]=$scope.currentDate;
				fields["Datum obračuna"]=$scope.currentDate;
				fields["Ukupno"]=$scope.ukupnoZaNaplatu;
				fields["Rabat"]=$scope.ukupanRabat;
				fields["Porez"]=$scope.ukupanPDV;
				fields["Iznos"]=$scope.ukupanIznos;
				var preduzece={};
				if(rowFieldsFromOrderForm["Poslovni partner"]){
					tableService.getTableById('Poslovni partner', rowFieldsFromOrderForm["Poslovni partner"]).then(
							function(response) {
								if(response.data.rows.length>0){
									tableService.getTableById('Preduzeće',response.data.rows[0].fields['Partner']).then(
										function(response){
											preduzece= response.data;
											fields["Tekući račun"]=preduzece.rows[0].fields["Tekući račun"];
											fields["Poziv na broj"]=preduzece.rows[0].fields["PIB"];
											fields["Status"]="O";
											fields["Dodatne napomene"]="";
											fields["Adresa isporuke"]=preduzece.rows[0].fields["Adresa"];
											fields["Broj kamiona"]="";
											fields["Prevoznik"]="";
											fields["Izdao robu"]="";
											fields["Preuzeo robu"]="";
											fields["Narudžba"]=rowFieldsFromOrderForm["Id"];
											fields["Broj narudžbe"]=rowFieldsFromOrderForm["Broj narudžbe"];
											fields["Poslovna godina"]=rowFieldsFromOrderForm["Poslovna godina"];
											fields["Godina poslovanja"]=rowFieldsFromOrderForm["Godina poslovanja"];
											fields["Poslovni partner"]=rowFieldsFromOrderForm["Poslovni partner"];
											fields["Naziv partnera"]=rowFieldsFromOrderForm["Naziv partnera"];
										}
									);
								}
							},
							function (response) {
								addElements(fields,rowFieldsFromOrderForm);
							}
					);
				}else{
					addElements(fields,rowFieldsFromOrderForm);
				}
				$scope.invoice.rows=[{"fields":fields}];
			},
			function(response){
				alert("Doslo je do greske prilikom preuzimanja tabele 'Faktura'.");
			}
		);
	}

	function addElements(fields,rowFieldsFromOrderForm){
		fields["Tekući račun"]="";
		fields["Poziv na broj"]="";
		fields["Status"]="O";
		fields["Dodatne napomene"]="";
		fields["Adresa isporuke"]="";
		fields["Broj kamiona"]="";
		fields["Prevoznik"]="";
		fields["Izdao robu"]="";
		fields["Preuzeo robu"]="";
		fields["Narudžba"]=rowFieldsFromOrderForm["Id"];
		fields["Broj narudžbe"]=rowFieldsFromOrderForm["Broj narudžbe"];
		fields["Poslovna godina"]=rowFieldsFromOrderForm["Poslovna godina"];
		fields["Godina poslovanja"]=rowFieldsFromOrderForm["Godina poslovanja"];
		fields["Poslovni partner"]=rowFieldsFromOrderForm["Poslovni partner"];
		fields["Naziv partnera"]=rowFieldsFromOrderForm["Naziv partnera"];
		return fields;
	}

	$scope.createInvoiceItems=function(){
		var tableName="Stavke fakture i otpremnice";
		tableService.getTableByName('Stavke_fakture_i_otpremnice').then(
			function(response) {
				$scope.ukupanIznos=0;
				$scope.ukupanRabat=0;
				$scope.cenaSaRabatom=0;
				$scope.ukupanPDV=0;
				$scope.ukupnoZaNaplatu=0;

				$scope.invoiceItems= response.data;
				var rowFieldsFromOrderFormItems={};
				$scope.invoiceItems.rows=[];
				tableService.getMaxId(tableName).then(
						function(response){
							var counter=response.data;
							for(var row in $scope.orderFormItems.rows){
								var narudzba=$scope.orderFormItems.rows[row].fields["Narudžba"];
								if(narudzba==$scope.selectedOrderForm){
									rowFieldsFromOrderFormItems=$scope.orderFormItems.rows[row].fields;
									var fields = {};
									var ukIznos=0;
									var ukRabat=0;
									var ukPDV=0;
									fields["Id"]=counter;
									fields["Faktura i otpremnica"]=$scope.invoice ? $scope.invoice.rows[0].fields.Id : "";
									fields["Broj fakture/otpremnice"]=$scope.invoice ? $scope.invoice.rows[0].fields["Broj fakture/otpremnice"] : "";
									fields["Katalog robe i usluga"]=rowFieldsFromOrderFormItems["Katalog robe i usluga"];
									fields["Naziv artikla"]=rowFieldsFromOrderFormItems["Naziv artikla"];
									fields["Količina"]=rowFieldsFromOrderFormItems["Količina stavke"];
									fields["Rabat"]=0;
									fields["Osnovica pdv"]=fields["Količina"] * rowFieldsFromOrderFormItems["Cena bez pdv"]-fields["Rabat"];
									fields["Jedinična cena stavke"]=rowFieldsFromOrderFormItems["Cena bez pdv"];
									$scope.itemsIdMap[counter]=$scope.orderFormItems.rows[row].fields["Id"];
									$scope.rabatMap[$scope.orderFormItems.rows[row].fields["Id"]]=0;
									$scope.invoiceItems.rows.push({"fields":fields});
								}
								counter++;
							}
							$scope.apply("",123456789);
						}
				);
			},
			function(response){
				alert("Doslo je do greske prilikom preuzimanja tabele 'Faktura'.");
			}
		);
	}

	$scope.apply=function(data,rowId){
		var orderItem=123456789;
		if(data==""){
			data=0;
			for(var row in $scope.invoiceItems.rows){
				if($scope.invoiceItems.rows[row].fields['Id']==rowId){
					$scope.invoiceItems.rows[row].fields['Rabat']=0;
				}
			}
		}
		if(rowId!=orderItem)
			orderItem=$scope.itemsIdMap[rowId];
		$scope.calculate(data,orderItem);
	}

	$scope.calculate=function(data,id){
		tableService.getCalculatedData($scope.selectedOrderForm,data,id).then(
				function(response){
					var calculatedData=response.data;
					$scope.rabatMap[id]=calculatedData["Rabat"];
					$scope.ukupanRabat=0;
					for(var rabat in $scope.rabatMap){
						$scope.ukupanRabat+=$scope.rabatMap[rabat];
					}
					$scope.cenaSaRabatom=calculatedData["Iznos"]-$scope.ukupanRabat;
					$scope.ukupnoZaNaplatu=$scope.cenaSaRabatom+calculatedData["PDV"];
					$scope.ukupanPDV=(calculatedData["PDV"]).toFixed(2);
					if($scope.ukupanRabat>0)
						$scope.ukupanRabat=($scope.ukupanRabat).toFixed(2);
					else
						$scope.ukupanRabat=0.00;
					$scope.ukupanIznos=(calculatedData["Iznos"]).toFixed(2);
					$scope.cenaSaRabatom=($scope.cenaSaRabatom).toFixed(2);
					$scope.ukupnoZaNaplatu=($scope.ukupnoZaNaplatu).toFixed(2);

					$scope.invoice.rows[0].fields["Ukupno"]=$scope.ukupnoZaNaplatu;
					$scope.invoice.rows[0].fields["Rabat"]=$scope.ukupanRabat;
					$scope.invoice.rows[0].fields["Porez"]=$scope.ukupanPDV;
					$scope.invoice.rows[0].fields["Iznos"]=$scope.ukupanIznos;
				}
		);
	}

	$scope.addInvoice=function(additionalNotes){
		var added=false;
		for(var i in $scope.addedInvoice){
			if($scope.addedInvoice[i]==$scope.invoice.rows[0].fields["Narudžba"]){
				alert("Faktura za trazenu narudzbenicu je vec kreirana.");
				added=true;
			}
		}
		if($scope.invoice && !added){
			$scope.invoice.rows[0].fields["Dodatne napomene"]=additionalNotes ? additionalNotes : "";
			$scope.invoice.rows[0].tableName="Faktura i otpremnica";
			$scope.invoice.rows[0].tableCode="Faktura_i_otpremnica";
			$scope.invoice.rows[0].fields=removeItems($scope.invoice.rows[0].fields);
			tableService.create($scope.invoice.tableName,$scope.invoice.rows[0]).then(
				function(response){
					$scope.additionalNotes="";
					if($scope.invoiceItems.rows.length>0)
						$scope.addInvoiceItems(response.data.rows[0].fields['Id']);
					else{
						$scope.showTable=false;
						alert("Uspesno kreirana faktura.");
					}
					$scope.addedInvoice.push($scope.invoice.rows[0].fields["Narudžba"]);

					$scope.ukupanIznos=0;
					$scope.ukupanPDV=0;
					$scope.ukupanRabat=0;
					$scope.ukupnoZaNaplatu=0;
				},
				function(response){
					alert("Doslo je do greske prilikom kreiranja fakture i stavke fakture.");
				}
			);
		}
	}

	function removeItems(items) {
	    var newItems = {};
	    angular.forEach(items, function(value, key){
	        if(key != "Ukupno" && key!="Iznos" && key!="Porez" && key!="Rabat")
	            newItems[key] = value;
	    });

	    return newItems;
	};

	$scope.addInvoiceItems=function(id){
		if($scope.invoiceItems.rows.length>0){
			var counter=0;
			for(var i=0;i<$scope.invoiceItems.rows.length;i++){
				var noviRabat=Number($scope.invoiceItems.rows[i].fields['Rabat']);
				$scope.invoiceItems.rows[i].fields['Rabat']=noviRabat;
				$scope.invoiceItems.rows[i].fields['Faktura i otpremnica']=id;
				$scope.invoiceItems.rows[i].tableName="Stavke fakture i otpremnice";
				$scope.invoiceItems.rows[i].tableCode="Stavke_fakture_i_otpremnice";
				tableService.create($scope.invoiceItems.tableName,$scope.invoiceItems.rows[i]).then(
					function(response){
						counter=counter+1;
						if(counter==$scope.invoiceItems.rows.length){
							$scope.showTable=false;
							alert("Uspesno kreirana faktura i stavke fakture.");
							$scope.rabatMap={};
						}
					},
					function(response){
						alert("Doslo je do greske prilikom kreiranja stavke fakture.");
					}
				);
			}
		}
	}
}]);
