app.controller('invoicingController',['$scope','tableService',function($scope, tableService){
	
	function init(){
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		$scope.currentDate=dd+"."+mm+"."+yyyy+".";
		
		$scope.addedInvoice=[];
		
		tableService.getTableRows("Narudzba").then(
			function(response){
				$scope.allOrderForms=response.data;
			}
		);
	}
	
	init();


	$scope.showOrderForm=function(){
		if($scope.selectedOrderForm){
			$scope.showTable=true;
			tableService.getTableByName('Narudzba').then(
				function(response) {
					$scope.orderForm= response.data;
					$scope.createInvoice();
					tableService.getTableByName('Stavka_narudzbe').then(
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
		tableService.getTableByName('Faktura').then(
			function(response) {
				$scope.invoice= response.data;
				var fields = {};
				var rowFieldsFromOrderForm={};
				for(var row in $scope.orderForm.rows){
					var id=$scope.orderForm.rows[row].fields.id;
					if(id==$scope.selectedOrderForm){
						rowFieldsFromOrderForm=$scope.orderForm.rows[row].fields;
						break;
					}
				}
				//id
				fields["id"]="5";
				fields["narudzba"]=rowFieldsFromOrderForm.id;
				fields["godina"]=rowFieldsFromOrderForm.godina;
				fields["broj_fakture"]=rowFieldsFromOrderForm.broj_narudzbe;
				fields["datum_narucivanja"]=rowFieldsFromOrderForm.datum_narucivanja;
				fields["datum_valute"]=$scope.currentDate;
				fields["datum_obracuna"]=$scope.currentDate;
				fields["partner"]=rowFieldsFromOrderForm.partner;
				fields["adresa_isporuke"]=rowFieldsFromOrderForm.adresa_isporuke;
				fields["tekuci_racun"]=rowFieldsFromOrderForm.tekuci_racun;
				fields["poziv_na_broj"] = rowFieldsFromOrderForm.poziv_na_broj;
				fields["status_fakture"]="U_procesu";
				fields["dodatne_napomene"]="";
				fields["ukupno"] = 0;
				
				$scope.invoice.rows=[{"fields":fields}];
			},
			function(response){
				alert("Doslo je do greske prilikom preuzimanja tabele 'Faktura'.");
			}
		);
	}
	
	$scope.createInvoiceItems=function(){
		tableService.getTableByName('Stavka_fakture').then(
			function(response) {
				$scope.ukupanIznos=0;
				$scope.ukupanRabat=0;
				$scope.cenaSaRabatom=0;
				$scope.ukupanPDV=0;
				$scope.ukupnoZaNaplatu=0;
				
				$scope.invoiceItems= response.data;
				var rowFieldsFromOrderFormItems={};
				var counter=5;
				$scope.invoiceItems.rows=[];
				for(var row in $scope.orderFormItems.rows){
					var narudzba=$scope.orderFormItems.rows[row].fields.narudzba;
					if(narudzba==$scope.selectedOrderForm){
						rowFieldsFromOrderFormItems=$scope.orderFormItems.rows[row].fields;
						counter++;
						var fields = {};
						//id
						fields["id"]=counter;
						fields["faktura"]=$scope.invoice ? $scope.invoice.rows[0].fields.id : "";
						fields["naziv_artikla"]=rowFieldsFromOrderFormItems.naziv_artikla;
						fields["sifra_artikla"]=rowFieldsFromOrderFormItems.sifra_artikla;
						fields["jedinica_mere"]=rowFieldsFromOrderFormItems.jedinica_mere;
						fields["kolicina"]=rowFieldsFromOrderFormItems.kolicina;
						fields["jedinicna_cena"]=rowFieldsFromOrderFormItems.jedinicna_cena;
						fields["rabat"]=rowFieldsFromOrderFormItems.rabat;
						fields["pdv"]=rowFieldsFromOrderFormItems.pdv;
						fields["cena_pdv"]=rowFieldsFromOrderFormItems.cena_pdv;
						fields["ukupno"] = rowFieldsFromOrderFormItems.ukupno;
						$scope.invoiceItems.rows.push({"fields":fields});
						
						$scope.ukupanIznos+=fields["kolicina"] * fields["jedinicna_cena"];
						$scope.ukupanRabat+=(fields["rabat"]/100 * fields["jedinicna_cena"]) * fields["kolicina"];
						$scope.ukupanPDV+=(fields["pdv"]/100 * fields["jedinicna_cena"]) * fields["kolicina"];
					}
				}
				$scope.cenaSaRabatom=$scope.ukupanIznos-$scope.ukupanRabat;
				$scope.ukupnoZaNaplatu=$scope.cenaSaRabatom+$scope.ukupanPDV;
				$scope.ukupanPDV=($scope.ukupanPDV).toFixed(2);
				$scope.ukupanRabat=($scope.ukupanRabat).toFixed(2);
				$scope.ukupanIznos=($scope.ukupanIznos).toFixed(2);
				$scope.cenaSaRabatom=($scope.cenaSaRabatom).toFixed(2);
				
				$scope.invoice.rows[0].fields.ukupno=$scope.ukupnoZaNaplatu;
			},
			function(response){
				alert("Doslo je do greske prilikom preuzimanja tabele 'Faktura'.");
			}
		);
	}
	
	
	$scope.isFormValid=function(){
		return $scope.invoice.broj_fakture && $scope.invoice.tip_fakture && 
			$scope.invoice.tekuci_racun && $scope.invoice.poziv_na_broj && 
			$scope.invoice.adresa_isporuke && $scope.invoice.broj_kamiona &&
			$scope.invoice.prevoznik && $scope.selectedOrderForm;
	}
	
	$scope.addInvoice=function(additionalNotes){
		var added=false;
		for(var i in $scope.addedInvoice){
			if($scope.addedInvoice[i]==$scope.invoice.rows[0].fields.narudzba){
				alert("Faktura za trazenu narudzbenicu je vec kreirana.");
				added=true;
			}
		}
		if($scope.invoice && !added){
			$scope.invoice.rows[0].fields.dodatne_napomene=additionalNotes ? additionalNotes : "";
			tableService.addTableRow($scope.invoice.tableName,$scope.invoice.rows).then(
				function(response){
					$scope.addedInvoice.push($scope.invoice.rows[0].fields.narudzba);
					$scope.addInvoiceItems();
					console.log(response.data);
				},
				function(response){
					alert("Doslo je do greske prilikom kreiranja fakture i stavke fakture.");
				}
			);
		}
	}
	
	$scope.addInvoiceItems=function(){
		if($scope.invoiceItems){
			tableService.addTableRow($scope.invoiceItems.tableName,$scope.invoiceItems.rows).then(
				function(response){
					console.log(response.data);
					alert("Uspesno kreirana faktura i stavke fakture.");
				},
				function(response){
					alert("Doslo je do greske prilikom kreiranja fakture i stavke fakture.");
				}
			);
		}
	}
}]);