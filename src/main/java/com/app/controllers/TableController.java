package com.app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.FileDownloadDTO;
import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;

@RestController
@RequestMapping(value = "/table")
public class TableController {

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getAllUsers() {
		ArrayList<TableDTO> tables = getMockData();
		ArrayList<String> names = new ArrayList<String>();
		for (TableDTO table : tables) {
			if (!names.contains(table.getTableName()))
				names.add(table.getTableName());
		}
		return new ResponseEntity<>(names, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getByName/{name}", method=RequestMethod.GET)
 	public ResponseEntity<TableDTO> getByName(@PathVariable String name)
 	{	
 		ArrayList<TableDTO> tables = getMockData();
 		TableDTO requestedTable = null;
 		for (TableDTO table : tables){
 			if (table.getTableName().equals(name)){
 				requestedTable = table;
 			}
 		}
 		return new ResponseEntity<>(requestedTable, HttpStatus.OK);
 	}
	
	@RequestMapping(value="/getDocChild/{parentName}/{parentId}", method=RequestMethod.GET)
	public ResponseEntity<TableDTO> getDocChild(@PathVariable String parentName, @PathVariable String parentId){
		Long id = Long.valueOf(parentId);
		ArrayList<TableDTO> tables = getMockData();
		TableDTO requestedTable = null;
		String name = null;
		for (TableDTO table : tables){
			if (table.getTableName().equals(parentName)){
				name = table.getDocumentChildName();
				break;
			}
		}
		for (TableDTO table : tables){
			if (table.getTableName().equals(name)){
				requestedTable = table;
				break;
			}
		}
		ArrayList<TableRowDTO> rows = new ArrayList<TableRowDTO>();
		for (int i=0; i < requestedTable.getRows().size(); i++){
			if (((Integer)requestedTable.getRows().get(i).getFields().get("parentId")) == id.longValue()){
				rows.add(requestedTable.getRows().get(i));
			}
		}
		requestedTable.setRows(rows);
		return new ResponseEntity<>(requestedTable, HttpStatus.OK);
	}


	@RequestMapping(value="/addRow/{tableName}",method=RequestMethod.POST)
	public ResponseEntity<ArrayList<TableRowDTO>> addRow(@RequestBody ArrayList<TableRowDTO> rows,@PathVariable String tableName){
		return new ResponseEntity<>(rows,HttpStatus.OK);
	}

	@RequestMapping(value="/getTableRows/{tableName}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getTableRows(@PathVariable String tableName){
		ArrayList<TableDTO> tables = getData();
		ArrayList<String> names = new ArrayList<String>();
		for (TableDTO table : tables) {
			if (table.getTableName().equals(tableName) && !names.contains(table.getTableName())){
				for(TableRowDTO row:table.getRows()){
					names.add(row.getFields().get("id").toString());
				}
				break;
			}
		}
		return new ResponseEntity<>(names, HttpStatus.OK);
	}
	
	//ista kao getByName, samo mockovani podaci drugaciji
	@RequestMapping(value="/getTable/{tableName}",method=RequestMethod.GET)
	public ResponseEntity<TableDTO> getTableByName(@PathVariable String tableName){
		ArrayList<TableDTO> tables = getData();
		TableDTO requestedTable =new TableDTO();
		for (TableDTO table : tables) {
			if (table.getTableName().equals(tableName)) {
				requestedTable = table;
				break;
			}
		}
		return new ResponseEntity<>(requestedTable, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addRow",method=RequestMethod.POST)
	public ResponseEntity<Object> addRow(@RequestBody TableRowDTO row){
		printRow(row, "Create");		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value="/editRow",method=RequestMethod.PATCH)
	public ResponseEntity<Object> editRow(@RequestBody TableRowDTO row){		
		printRow(row, "Edit");		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/{tableName}/{rowId}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteRow(@PathVariable String tableName, @PathVariable String rowId){		
		System.out.println("Deleting from table " + tableName + " with ID " + rowId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void printRow(TableRowDTO row, String operation) {
		System.out.println("Operacija: " + operation + "\nTabela: " + row.getTableName() + "\n");
		for (String key : row.getFields().keySet()) {
			System.out.println("\t" + key + " : " + row.getFields().get(key));
		}
		System.out.println("---------------------");
	}
	
	

	private ArrayList<TableDTO> getMockData() {
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();

		ArrayList<TableFieldDTO> fields1 = new ArrayList<TableFieldDTO>();
		fields1.add(new TableFieldDTO("id", false, false, null, "number"));
		fields1.add(new TableFieldDTO("naziv", false, false, null, "text"));
		fields1.add(new TableFieldDTO("tip", false, false, null, "text"));
		fields1.add(new TableFieldDTO("godina", false, true, "Poslovna godina", "date"));

		ArrayList<TableRowDTO> rows1 = new ArrayList<TableRowDTO>();
		TableRowDTO row11 = new TableRowDTO();
		row11.getFields().put("id", 1);
		row11.getFields().put("naziv", "Faktura 1");
		row11.getFields().put("tip", "Prvi tip fakture");
		row11.getFields().put("godina", "Godina 1");
		TableRowDTO row12 = new TableRowDTO();
		row12.getFields().put("id", 2);
		row12.getFields().put("naziv", "Faktura 2");
		row12.getFields().put("tip", "Drugi tip fakture");
		row12.getFields().put("godina", "Godina 2");
		TableRowDTO row13 = new TableRowDTO();
		row13.getFields().put("id", 3);
		row13.getFields().put("naziv", "Faktura 3");
		row13.getFields().put("tip", "Drugi tip fakture");
		row13.getFields().put("godina", "Godina 1");
		rows1.add(row11);
		rows1.add(row12);
		rows1.add(row13);

		ArrayList<TableFieldDTO> fields2 = new ArrayList<TableFieldDTO>();
		fields2.add(new TableFieldDTO("id", false, false, null, "number"));
		fields2.add(new TableFieldDTO("parentId", false, true, "Faktura", "number"));
		fields2.add(new TableFieldDTO("vrednost", false, false, null, "text"));

		ArrayList<TableRowDTO> rows2 = new ArrayList<TableRowDTO>();
		TableRowDTO row21 = new TableRowDTO();
		row21.getFields().put("id", 1);
		row21.getFields().put("parentId", 1);
		row21.getFields().put("vrednost", "Stavka 1 od f1");
		TableRowDTO row22 = new TableRowDTO();
		row22.getFields().put("id", 2);
		row22.getFields().put("parentId", 1);
		row22.getFields().put("vrednost", "Stavka 2 od f1");
		TableRowDTO row23 = new TableRowDTO();
		row23.getFields().put("id", 3);
		row23.getFields().put("parentId", 2);
		row23.getFields().put("vrednost", "Stavka 1 od f2");
		rows2.add(row21);
		rows2.add(row22);
		rows2.add(row23);
		
		ArrayList<TableFieldDTO> fieldsPriceList = new ArrayList<TableFieldDTO>();
		fieldsPriceList.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsPriceList.add(new TableFieldDTO("naziv", false, false, null, "text"));
		fieldsPriceList.add(new TableFieldDTO("datum primene", false, false, null, "date"));
		fieldsPriceList.add(new TableFieldDTO("preduzece", false, true, "Preduzece", "number"));

		ArrayList<TableRowDTO> rows1Pricelist = new ArrayList<TableRowDTO>();
		TableRowDTO row1Pricelist = new TableRowDTO();
		row1Pricelist.getFields().put("id", 1);
		row1Pricelist.getFields().put("naziv", "Cenovnik 1");
		row1Pricelist.getFields().put("datum primene", "05.08.2015.");
		row1Pricelist.getFields().put("preduzece", "1");
		TableRowDTO row2Pricelist = new TableRowDTO();
		row2Pricelist.getFields().put("id", 2);
		row2Pricelist.getFields().put("naziv", "Cenovnik 2");
		row2Pricelist.getFields().put("datum primene", "12.12.2015.");
		row2Pricelist.getFields().put("preduzece", "2");
		TableRowDTO row3Pricelist = new TableRowDTO();
		row3Pricelist.getFields().put("id", 3);
		row3Pricelist.getFields().put("naziv", "Cenovnik 3");
		row3Pricelist.getFields().put("datum primene", "12.10.2015.");
		row3Pricelist.getFields().put("preduzece", "2");
		rows1Pricelist.add(row1Pricelist);
		rows1Pricelist.add(row2Pricelist);
		rows1Pricelist.add(row3Pricelist);

		ArrayList<TableFieldDTO> fieldsPriceListItem = new ArrayList<TableFieldDTO>();
		fieldsPriceListItem.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsPriceListItem.add(new TableFieldDTO("parentId", false, true, "Cenovnik", "number"));
		fieldsPriceListItem.add(new TableFieldDTO("jedinicna_cena", false, false, null, "number"));

		ArrayList<TableRowDTO> rows1PricelistItem = new ArrayList<TableRowDTO>();
		TableRowDTO row1PricelistItem = new TableRowDTO();
		row1PricelistItem.getFields().put("id", 1);
		row1PricelistItem.getFields().put("parentId", 1);
		row1PricelistItem.getFields().put("vrednost", "50");
		TableRowDTO row2PricelistItem = new TableRowDTO();
		row2PricelistItem.getFields().put("id", 2);
		row2PricelistItem.getFields().put("parentId", 1);
		row2PricelistItem.getFields().put("vrednost", "100");
		TableRowDTO row3PricelistItem = new TableRowDTO();
		row3PricelistItem.getFields().put("id", 3);
		row3PricelistItem.getFields().put("parentId", 2);
		row3PricelistItem.getFields().put("vrednost", "150");
		rows1PricelistItem.add(row1PricelistItem);
		rows1PricelistItem.add(row2PricelistItem);
		rows1PricelistItem.add(row3PricelistItem);
		
		ArrayList<TableFieldDTO> fieldsCompany = new ArrayList<TableFieldDTO>();
		fieldsCompany.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsCompany.add(new TableFieldDTO("naziv", false, false, null, "text"));

		ArrayList<TableRowDTO> rows1Company = new ArrayList<TableRowDTO>();
		TableRowDTO row1Company = new TableRowDTO();
		row1Company.getFields().put("id", 1);
		row1Company.getFields().put("naziv", "Preduzece 1");
		TableRowDTO row2Company = new TableRowDTO();
		row2Company.getFields().put("id", 2);
		row2Company.getFields().put("naziv", "Preduzece 2");
		rows1Company.add(row1Company);
		rows1Company.add(row2Company);

		tables.add(new TableDTO("Faktura", fields1, rows1, true,
				"Stavka fakture", null, null));
		tables.add(new TableDTO("Stavka fakture", fields2, rows2, true, null,
				null, null));
		
		tables.add(new TableDTO("Cenovnik", fieldsPriceList, rows1Pricelist, true,
				"Stavka cenovnika", null, null));
		tables.add(new TableDTO("Stavka cenovnika", fieldsPriceListItem, rows1PricelistItem, true, null,
				null, null));
		tables.add(new TableDTO("Preduzece", fieldsCompany, rows1Company, false,
				null, null, null));
		return tables;
	}


	private ArrayList<TableDTO> getData() {
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();

		//-----------------FAKTURA-------------------------------
		
		ArrayList<TableFieldDTO> invoiceFields= new ArrayList<TableFieldDTO>();
		invoiceFields.add(new TableFieldDTO("id", false, false, null, "number"));
		invoiceFields.add(new TableFieldDTO("narudzba", false, true, "Narudzba","number"));
		invoiceFields.add(new TableFieldDTO("godina", false, true, "Poslovna godina","number"));
		invoiceFields.add(new TableFieldDTO("broj_fakture", false, false, null,"number"));
		invoiceFields.add(new TableFieldDTO("datum_narucivanja", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("datum_valute", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("datum_obracuna", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("partner", false, true, "Poslovni partner","text"));
		invoiceFields.add(new TableFieldDTO("adresa_isporuke", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("tekuci_racuna", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("poziv_na_broj", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("status_fakture", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("dodatne_napomene", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("ukupno", false, false, null,"number"));
		
		ArrayList<TableRowDTO> invoiceRows= new ArrayList<TableRowDTO>();
		TableRowDTO invoiceValues= new TableRowDTO();
		invoiceValues.getFields().put("id", 1);
		invoiceValues.getFields().put("narudzba", 1);
		invoiceValues.getFields().put("godina", 2016);
		invoiceValues.getFields().put("broj_fakture", 1564);
		invoiceValues.getFields().put("datum_narucivanja", "7.6.2016.");
		invoiceValues.getFields().put("datum_valute", "7.6.2016.");
		invoiceValues.getFields().put("datum_obracuna", "7.6.2016.");
		invoiceValues.getFields().put("partner", "Partner 2");
		invoiceValues.getFields().put("adresa_isporuke", "Adr 1");
		invoiceValues.getFields().put("tekuci_racun", "rac");
		invoiceValues.getFields().put("poziv_na_broj", "85468596548526");
		invoiceValues.getFields().put("status_fakture", "U_procesu");
		invoiceValues.getFields().put("dodatne_napomene", "Napomena 1");
		invoiceValues.getFields().put("ukupno", 13563.90);
		invoiceRows.add(invoiceValues);

		//--------------STAVKA FAKTURE-------------------------
		
		ArrayList<TableFieldDTO> invoiceItemFields= new ArrayList<TableFieldDTO>();
		invoiceItemFields.add(new TableFieldDTO("id", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("faktura", false, true, "Faktura","number"));
		invoiceItemFields.add(new TableFieldDTO("naziv_artikla", false, false, null,"text"));
		invoiceItemFields.add(new TableFieldDTO("sifra_artikla", false, false, null,"text"));
		invoiceItemFields.add(new TableFieldDTO("jedinica_mere", false, false, null,"text"));
		invoiceItemFields.add(new TableFieldDTO("kolicina", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("jedinicna_cena", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("rabat", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("pdv", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("cena_pdv", false, false, null,"number"));
		invoiceItemFields.add(new TableFieldDTO("ukupno", false, false, null,"number"));

		ArrayList<TableRowDTO> invoiceItemRows = new ArrayList<TableRowDTO>();
		TableRowDTO invoiceItemValues= new TableRowDTO();
		invoiceItemValues.getFields().put("id", 1);
		invoiceItemValues.getFields().put("faktura", 1);
		invoiceItemValues.getFields().put("naziv_artikla", "Sunoko secer");
		invoiceItemValues.getFields().put("sifra_artikla", "8652659365486");
		invoiceItemValues.getFields().put("jedinica_mere", "kom");
		invoiceItemValues.getFields().put("kolicina", 20);
		invoiceItemValues.getFields().put("jedinicna_cena", 70.00);
		invoiceItemValues.getFields().put("rabat", 6);
		invoiceItemValues.getFields().put("pdv", 20);
		invoiceItemValues.getFields().put("cena_pdv", 84.00);
		invoiceItemValues.getFields().put("ukupno", 1680.00);
		invoiceItemRows.add(invoiceItemValues);
		
		//---------------NARUDZBA-------------------------------------
		
		ArrayList<TableFieldDTO> orderFormFields = new ArrayList<TableFieldDTO>();
		orderFormFields.add(new TableFieldDTO("id", false, false, null,"number"));
		orderFormFields.add(new TableFieldDTO("godina", false, true, "Poslovna godina","number"));
		orderFormFields.add(new TableFieldDTO("broj_narudzbe", false, false, null,"number"));
		orderFormFields.add(new TableFieldDTO("datum_narucivanja", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("rok_isporuke", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("partner", false, true, "Poslovni partner","text"));
		orderFormFields.add(new TableFieldDTO("adresa_isporuke", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("tekuci_racuna", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("poziv_na_broj", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("status_narudzbe", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("status_fakture", false, false, null,"text"));
		
		ArrayList<TableRowDTO> orderFormRows = new ArrayList<TableRowDTO>();
		TableRowDTO orderFormValues= new TableRowDTO();
		orderFormValues.getFields().put("id", 1);
		orderFormValues.getFields().put("godina", 2016);
		orderFormValues.getFields().put("broj_narudzbe", 1564);
		orderFormValues.getFields().put("datum_narucivanja", "5.6.2016.");
		orderFormValues.getFields().put("rok_isporuke", "12.6.2016");
		orderFormValues.getFields().put("partner", "Partner 1");
		orderFormValues.getFields().put("adresa_isporuke", "Adr1");
		orderFormValues.getFields().put("tekuci_racun", "rac");
		orderFormValues.getFields().put("poziv_na_broj", "8546357859624");
		orderFormValues.getFields().put("status_narudzbe", "U_procesu");
		orderFormValues.getFields().put("status_fakture", "U_procesu");
		orderFormRows.add(orderFormValues);

		//---------------STAVKA NARUDZBE-------------------------
		
		ArrayList<TableFieldDTO> orderFormItemFields= new ArrayList<TableFieldDTO>();
		orderFormItemFields.add(new TableFieldDTO("id", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("narudzba", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("naziv_artikla", false, false, null,"text"));
		orderFormItemFields.add(new TableFieldDTO("sifra_artikla", false, false, null,"text"));
		orderFormItemFields.add(new TableFieldDTO("jedinica_mere", false, false, null,"text"));
		orderFormItemFields.add(new TableFieldDTO("kolicina", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("jedinicna_cena", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("rabat", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("pdv", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("cena_pdv", false, false, null,"number"));
		orderFormItemFields.add(new TableFieldDTO("ukupno", false, false, null,"number"));

		ArrayList<TableRowDTO> orderFormItemRows = new ArrayList<TableRowDTO>();
		TableRowDTO orderFormItemValues= new TableRowDTO();
		orderFormItemValues.getFields().put("id", 1);
		orderFormItemValues.getFields().put("narudzba", 1);
		orderFormItemValues.getFields().put("naziv_artikla", "Sunoko secer");
		orderFormItemValues.getFields().put("sifra_artikla", "8652659365486");
		orderFormItemValues.getFields().put("jedinica_mere", "kom");
		orderFormItemValues.getFields().put("kolicina", 30);
		orderFormItemValues.getFields().put("jedinicna_cena", 70.00);
		orderFormItemValues.getFields().put("rabat", 6);
		orderFormItemValues.getFields().put("pdv", 20);
		orderFormItemValues.getFields().put("cena_pdv", 84.00);
		orderFormItemValues.getFields().put("ukupno", 2520.00);
		orderFormItemRows.add(orderFormItemValues);
		
		TableRowDTO orderFormItemValues2= new TableRowDTO();
		orderFormItemValues2.getFields().put("id", 2);
		orderFormItemValues2.getFields().put("narudzba", 1);
		orderFormItemValues2.getFields().put("naziv_artikla", "Grand kafa 200g");
		orderFormItemValues2.getFields().put("sifra_artikla", "8652659365895");
		orderFormItemValues2.getFields().put("jedinica_mere", "kom");
		orderFormItemValues2.getFields().put("kolicina", 30);
		orderFormItemValues2.getFields().put("jedinicna_cena", 180.00);
		orderFormItemValues2.getFields().put("rabat", 6);
		orderFormItemValues2.getFields().put("pdv", 20);
		orderFormItemValues2.getFields().put("cena_pdv", 216.00);
		orderFormItemValues2.getFields().put("ukupno", 6480.00);
		orderFormItemRows.add(orderFormItemValues2);
		
		TableRowDTO orderFormItemValues3= new TableRowDTO();
		orderFormItemValues3.getFields().put("id", 3);
		orderFormItemValues3.getFields().put("narudzba", 1);
		orderFormItemValues3.getFields().put("naziv_artikla", "Grand kafa 100g");
		orderFormItemValues3.getFields().put("sifra_artikla", "8652659365894");
		orderFormItemValues3.getFields().put("jedinica_mere", "kom");
		orderFormItemValues3.getFields().put("kolicina", 20);
		orderFormItemValues3.getFields().put("jedinicna_cena", 86.00);
		orderFormItemValues3.getFields().put("rabat", 6);
		orderFormItemValues3.getFields().put("pdv", 20);
		orderFormItemValues3.getFields().put("cena_pdv", 103.20);
		orderFormItemValues3.getFields().put("ukupno", 2064.00);
		orderFormItemRows.add(orderFormItemValues3);
		
		tables.add(new TableDTO("Narudzba", orderFormFields, orderFormRows, true,
				"Stavka_narudzbe", null, null));
		tables.add(new TableDTO("Faktura", invoiceFields, invoiceRows, true,
				"Stavka_fakture", null, null));
		tables.add(new TableDTO("Stavka_fakture", invoiceItemFields, invoiceItemRows, true,
				null, null, null));
		tables.add(new TableDTO("Stavka_narudzbe", orderFormItemFields, orderFormItemRows, true,
				null, null, null));
		return tables;
	}
	
}
