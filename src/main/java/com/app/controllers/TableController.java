package com.app.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Date;
import java.util.HashMap;
>>>>>>> f3f9320d81097ed89e58e96265009d4583d6972b

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.KifDTO;
import com.app.DTO.PricelistDTO;
import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;
import com.app.helpers.ConversionHelper;
import com.app.services.IGenericService;

@RestController
@RequestMapping(value = "/table")
public class TableController {

	
	@Autowired
	private IGenericService crudService;
	
	@Autowired
	private ApplicationContext applicationContext;
		
	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody TableRowDTO row) {
		if(crudService.create(row)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody TableRowDTO row) {
		if(crudService.update(row)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path = "/delete/{tableCode}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable String tableCode, @PathVariable Long id) {
		if(crudService.delete(id, tableCode)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path = "/getById/{tableCode}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@PathVariable String tableCode, @PathVariable Long id) {				
		TableDTO result = crudService.getById(id, tableCode);
		
		if(result == null) {
			return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
	}
	
	@RequestMapping(path = "/getAll/{tableCode}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAll(@PathVariable String tableCode) {	
		TableDTO result = crudService.getAll(tableCode);
		if (result == null){
			result = crudService.getMetaData(tableCode);	
		}
		if(result == null) {
			return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
	}
	
	@RequestMapping(path = "/getAllNames", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllEditableTableNames() {		
		return new ResponseEntity<Object>(crudService.getEditableTables(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getMetaData/{tableCode}", method = RequestMethod.GET)
	public ResponseEntity<Object> getMetaData(@PathVariable String tableCode) {		
		TableDTO result = crudService.getMetaData(tableCode);
		
		if(result == null) {
			return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getDocChild/{parentTableCode}/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getDocChild(@PathVariable String parentTableCode, @PathVariable String parentId) {
		
		TableDTO requestedTable = crudService.getTableByParent(parentTableCode, parentId);
		
		if(requestedTable == null) {
			return new ResponseEntity<>(requestedTable, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(requestedTable, HttpStatus.OK);
		}		
	}
	
	@RequestMapping(path = "/filter", method = RequestMethod.POST)
	public ResponseEntity<Object> filter(@RequestBody TableRowDTO filterRow) {
	TableDTO requestedTable = crudService.getFilteredTable(filterRow);
		
		if(requestedTable == null) {
			return new ResponseEntity<Object>(requestedTable, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(requestedTable, HttpStatus.OK);
		}
	}
	
	@RequestMapping(path="/generateKIF", method=RequestMethod.POST)
	public ResponseEntity<Object> generateKIF(@RequestBody KifDTO info){
		DataSource ds = (DataSource)applicationContext.getBean("dataSource");
		try {
			Connection c = ds.getConnection();
			crudService.generateKIF(c, info);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/companiesForKIF", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getCompaniesForKIF() {
		
		TableDTO requestedTable = crudService.getCompaniesForKIF();
		
		if(requestedTable == null) {
			return new ResponseEntity<>(requestedTable, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(requestedTable, HttpStatus.OK);
		}		
	}
	@RequestMapping(path="/generatePDF/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> generatePDF(@PathVariable String id){
		DataSource ds = (DataSource)applicationContext.getBean("dataSource");
		try {
			Connection c = ds.getConnection();
			crudService.generatePDF(c, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.OK);		
	}	
	
	@RequestMapping(value = "/maxId/{tableCode}", method = RequestMethod.GET)
	public ResponseEntity<Object> getMaxID(@PathVariable String tableCode) {
		
		TableDTO requestedTable = crudService.getAll(tableCode);
		
		if(requestedTable != null) {
			Long id=(Long)requestedTable.getRows().get(requestedTable.getRows().size()-1).getFields().get("Id")+1;
			return new ResponseEntity<Object>(id, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@RequestMapping(path = "/getAllOrders", method = RequestMethod.GET)
	public ResponseEntity<Object> getAll() {	
		String invoiceCode="Faktura_i_otpremnica";
		String orderCode="Narudzba";
		ArrayList<Long> result=new ArrayList<>();
		TableDTO invoiceTable= crudService.getAll(invoiceCode);
		TableDTO orderTable= crudService.getAll(orderCode);
		if (invoiceTable != null && orderTable!=null){
			for(int i=0;i<orderTable.getRows().size();i++){
				Long id=(Long)orderTable.getRows().get(i).getFields().get("Id");
				boolean equals=false;
				for(int j=0;j<invoiceTable.getRows().size();j++){
					Long orderId=(Long)invoiceTable.getRows().get(i).getFields().get("Narudžba");
					if(id.equals(orderId)){
						equals=true;
					}
				}
				if(!equals){
					result.add(id);
				}
			}

			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
	}
	
//------------------------------------------------------------------------------------------------------------------
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getAllUsers() {
//		ArrayList<TableDTO> tables = getMockData();
		ArrayList<String> names = new ArrayList<String>();
//		for (TableDTO table : tables) {
//			if (!names.contains(table.getTableName()) && ((table.isDocumentPattern() && !(table.getDocumentChildName() == null))
//					|| (!table.isDocumentPattern())))
//				names.add(table.getTableName());
//		}
		names.add("Cenovnik");
		names.add("Faktura_otpremnica");
		return new ResponseEntity<>(names, HttpStatus.OK);
	}

	private ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (String stringValue : stringArray) {
			try {
				// Convert String to Integer, and store it into integer array
				// list.
				result.add(Integer.parseInt(stringValue));
			} catch (NumberFormatException nfe) {
			}
		}
		return result;
	}

	@RequestMapping(path = "/addPricelist", method = RequestMethod.POST)
	public ResponseEntity<Object> addPricelist(@RequestBody PricelistDTO pricelist) {
		TableRowDTO cenovnik = pricelist.getParent();
		cenovnik.getFields().remove("Id");
		crudService.create(cenovnik);
		ArrayList<TableRowDTO> stavke = pricelist.getChild();
		for (TableRowDTO row : stavke){
			row.getFields().remove("Id");
			crudService.create(row);
		}
		/*ArrayList<String> ids = new ArrayList<String>();
		for (TableRowDTO row : rows1Pricelist) {
			ids.add(row.getFields().get("Id").toString());
		}
		ArrayList<Integer> resultList = getIntegerArray(ids);
		int max = Collections.max(resultList);

		pricelist.getParent().getFields().put("naziv", "Cenovnik " + (max + 1));
		pricelist.getParent().getFields().put("Id", (max + 1));
		
		addedRowsPricelist.add(pricelist.getParent());
		for (TableRowDTO row : pricelist.getChild()) {
			row.getFields().put("cenovnik", (max+1));
			addedRowsPricelistItem.add(row);
		}
		System.out.println("Podaci o cenovniku: \n\n" + pricelist.getParent());
		System.out.println("Stavke cenovnika: \n");
		for (TableRowDTO row : pricelist.getChild()) {
			System.out.println(row);
		}*/
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(path = "/addTableRow", method = RequestMethod.POST)
	public ResponseEntity<Object> addTableRow(@RequestBody TableRowDTO row) {

		/*ArrayList<String> ids = new ArrayList<String>();
		for (TableRowDTO roww : rows1PricelistItem) {
			ids.add(roww.getFields().get("id").toString());
		}
		ArrayList<Integer> resultList = getIntegerArray(ids);
		int max = Collections.max(resultList);

		row.getFields().put("id", (max + 1));

		addedRowsPricelistItem.add(row);
		System.out.println(row);*/
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(path = "/deleteTableRow", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteTableRow(@RequestBody TableRowDTO row) {

		//deleteRowsPricelist.add(row);
		System.out.println(row);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getByName(@PathVariable String name) {
		ArrayList<TableDTO> tables = getMockData();
		TableDTO requestedTable = null;
		for (TableDTO table : tables) {
			if (table.getTableName().equals(name)) {
				requestedTable = table;
			}
		}
		return new ResponseEntity<>(requestedTable, HttpStatus.OK);
	}

	@RequestMapping(value = "/filterNextTable/{childName}/{parentName}/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getFilteredForNext(@PathVariable String childName, @PathVariable String parentName, @PathVariable String parentId) {
		Long id = Long.valueOf(parentId);
		TableDTO requestedTable = null;
		requestedTable = crudService.getAll(childName);
		ArrayList<TableRowDTO> rows = new ArrayList<TableRowDTO>();
		for (int i=0; i < requestedTable.getRows().size(); i++){
			if (((Long)requestedTable.getRows().get(i).
					getFields().
					get(ConversionHelper.getTableName(parentName))) == id){
				rows.add(requestedTable.getRows().get(i));
			}
		}
		requestedTable.setRows(rows);
		return new ResponseEntity<>(requestedTable, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Object> addTable(@RequestBody TableDTO table) {

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/addRow/{tableName}", method = RequestMethod.POST)
	public ResponseEntity<ArrayList<TableRowDTO>> addRow(@RequestBody ArrayList<TableRowDTO> rows,
			@PathVariable String tableName) {
		return new ResponseEntity<>(rows, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTableRows/{tableName}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getTableRows(@PathVariable String tableName) {
		ArrayList<TableDTO> tables = getMockData();
		ArrayList<String> names = new ArrayList<String>();
		for (TableDTO table : tables) {
			if (table.getTableName().equals(tableName) && !names.contains(table.getTableName())) {
				for (TableRowDTO row : table.getRows()) {
					names.add(row.getFields().get("id").toString());
				}
				break;
			}
		}
		return new ResponseEntity<>(names, HttpStatus.OK);
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
	

	
	@RequestMapping(path="/generateXML/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> generateXML(@PathVariable String id){
		Long idL=new Long(id);
		String filePath="src/main/webapp/downloads/faktura.xml";
		if(crudService.generateXML(idL,filePath)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private ArrayList<TableDTO> getMockData() {
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();

		/*
		ArrayList<TableFieldDTO> invoiceFields= new ArrayList<TableFieldDTO>();
		invoiceFields.add(new TableFieldDTO("id", false, false, null, "number"));
		invoiceFields.add(new TableFieldDTO("narudzba", false, true, "Narudzba","number"));
		invoiceFields.add(new TableFieldDTO("godina", false, false, null,"number"));
		invoiceFields.add(new TableFieldDTO("broj_fakture", false, false, null,"number"));
		invoiceFields.add(new TableFieldDTO("datum_narucivanja", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("datum_valute", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("datum_obracuna", false, false, null,"text"));
		invoiceFields.add(new TableFieldDTO("poslovni partner", false, true, "Poslovni partner", "number"));
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
		invoiceValues.getFields().put("poslovni partner", 2);
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
		TableRowDTO invoiceItemValues1= new TableRowDTO();
		invoiceItemValues1.getFields().put("id", 1);
		invoiceItemValues1.getFields().put("faktura", 1);
		invoiceItemValues1.getFields().put("naziv_artikla", "Sunoko secer");
		invoiceItemValues1.getFields().put("sifra_artikla", "8652659365486");
		invoiceItemValues1.getFields().put("jedinica_mere", "kom");
		invoiceItemValues1.getFields().put("kolicina", 20);
		invoiceItemValues1.getFields().put("jedinicna_cena", 70.00);
		invoiceItemValues1.getFields().put("rabat", 6);
		invoiceItemValues1.getFields().put("pdv", 20);
		invoiceItemValues1.getFields().put("cena_pdv", 84.00);
		invoiceItemValues1.getFields().put("ukupno", 1680.00);
		invoiceItemRows.add(invoiceItemValues1);
		
		TableRowDTO invoiceItemValues2= new TableRowDTO();
		invoiceItemValues2.getFields().put("id", 2);
		invoiceItemValues2.getFields().put("faktura", 1);
		invoiceItemValues2.getFields().put("naziv_artikla", "Secerna vuna");
		invoiceItemValues2.getFields().put("sifra_artikla", "1652629363486");
		invoiceItemValues2.getFields().put("jedinica_mere", "kom");
		invoiceItemValues2.getFields().put("kolicina", 20);
		invoiceItemValues2.getFields().put("jedinicna_cena", 70.00);
		invoiceItemValues2.getFields().put("rabat", 6);
		invoiceItemValues2.getFields().put("pdv", 20);
		invoiceItemValues2.getFields().put("cena_pdv", 84.00);
		invoiceItemValues2.getFields().put("ukupno", 1680.00);
		invoiceItemRows.add(invoiceItemValues2);
		
		//---------------POSLOVNI PARTNER----------------------------
		
		ArrayList<TableFieldDTO> pPartnerFields = new ArrayList<TableFieldDTO>();
		pPartnerFields.add(new TableFieldDTO("id", false, false, null,"number"));
		pPartnerFields.add(new TableFieldDTO("naziv", false, false, null,"text"));
		
		ArrayList<TableRowDTO> pParnterRows = new ArrayList<TableRowDTO>();
		TableRowDTO pParnterValues= new TableRowDTO();
		pParnterValues.getFields().put("id", 1);
		pParnterValues.getFields().put("naziv", "Partner 1");
		TableRowDTO pParnterValues2= new TableRowDTO();
		pParnterValues2.getFields().put("id", 2);
		pParnterValues2.getFields().put("naziv", "Partner 2");
		pParnterRows.add(pParnterValues);
		pParnterRows.add(pParnterValues2);
		
		//---------------NARUDZBA-------------------------------------
		
		ArrayList<TableFieldDTO> orderFormFields = new ArrayList<TableFieldDTO>();
		orderFormFields.add(new TableFieldDTO("id", false, false, null,"number"));
		orderFormFields.add(new TableFieldDTO("godina", false, false, null,"number"));
		orderFormFields.add(new TableFieldDTO("broj_narudzbe", false, false, null,"number"));
		orderFormFields.add(new TableFieldDTO("datum_narucivanja", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("rok_isporuke", false, false, null,"text"));
		orderFormFields.add(new TableFieldDTO("poslovni partner", false, true, "Poslovni partner", "number"));
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
		orderFormValues.getFields().put("poslovni partner", 1);
		orderFormValues.getFields().put("adresa_isporuke", "Adr1");
		orderFormValues.getFields().put("tekuci_racun", "rac");
		orderFormValues.getFields().put("poziv_na_broj", "8546357859624");
		orderFormValues.getFields().put("status_narudzbe", "U_procesu");
		orderFormValues.getFields().put("status_fakture", "U_procesu");
		orderFormRows.add(orderFormValues);

		TableRowDTO orderFormValues2= new TableRowDTO();
		orderFormValues2.getFields().put("id", 2);
		orderFormValues2.getFields().put("godina", 2016);
		orderFormValues2.getFields().put("broj_narudzbe", 1565);
		orderFormValues2.getFields().put("datum_narucivanja", "5.6.2016.");
		orderFormValues2.getFields().put("rok_isporuke", "12.6.2016");
		orderFormValues2.getFields().put("poslovni partner", 1);
		orderFormValues2.getFields().put("adresa_isporuke", "Adr1");
		orderFormValues2.getFields().put("tekuci_racun", "rac");
		orderFormValues2.getFields().put("poziv_na_broj", "8546357859624");
		orderFormValues2.getFields().put("status_narudzbe", "U_procesu");
		orderFormValues2.getFields().put("status_fakture", "U_procesu");
		orderFormRows.add(orderFormValues2);

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
		
		TableRowDTO orderFormItemValues4= new TableRowDTO();
		orderFormItemValues4.getFields().put("id", 1);
		orderFormItemValues4.getFields().put("narudzba", 2);
		orderFormItemValues4.getFields().put("naziv_artikla", "Grand kafa 100g");
		orderFormItemValues4.getFields().put("sifra_artikla", "8652659365894");
		orderFormItemValues4.getFields().put("jedinica_mere", "kom");
		orderFormItemValues4.getFields().put("kolicina", 20);
		orderFormItemValues4.getFields().put("jedinicna_cena", 86.00);
		orderFormItemValues4.getFields().put("rabat", 6);
		orderFormItemValues4.getFields().put("pdv", 20);
		orderFormItemValues4.getFields().put("cena_pdv", 103.20);
		orderFormItemValues4.getFields().put("ukupno", 2064.00);
		orderFormItemRows.add(orderFormItemValues4);
		
		
		ArrayList<String> narudzbaParents = new ArrayList<String>();
		narudzbaParents.add("Poslovni partner");
		ArrayList<String> fakturaParents = new ArrayList<String>();
		fakturaParents.add("Poslovni partner");
		ArrayList<String> pPartnerChildren = new ArrayList<String>();
		pPartnerChildren.add("Faktura");
		pPartnerChildren.add("Narudzba");
		
		tables.add(new TableDTO("Poslovni partner", pPartnerFields, pParnterRows, false,
				null, pPartnerChildren, null));
		tables.add(new TableDTO("Narudzba", orderFormFields, orderFormRows, true,
				"Stavka_narudzbe", null, narudzbaParents));
		tables.add(new TableDTO("Faktura", invoiceFields, invoiceRows, true,
				"Stavka_fakture", null, fakturaParents));
		tables.add(new TableDTO("Stavka_fakture", invoiceItemFields, invoiceItemRows, true,
				null, null, null));
		tables.add(new TableDTO("Stavka_narudzbe", orderFormItemFields, orderFormItemRows, true,
				null, null, null));
		
		ArrayList<TableFieldDTO> fieldsPriceList = new ArrayList<TableFieldDTO>();
		fieldsPriceList.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsPriceList.add(new TableFieldDTO("naziv", false, false, null, "text"));
		fieldsPriceList.add(new TableFieldDTO("datum_primene", false, false, null, "text"));
		fieldsPriceList.add(new TableFieldDTO("preduzece", false, true, "Preduzece", "number"));

		rows1Pricelist = new ArrayList<TableRowDTO>();
		TableRowDTO row1Pricelist = new TableRowDTO();
		row1Pricelist.getFields().put("id", 1);
		row1Pricelist.getFields().put("naziv", "Cenovnik 1");
		row1Pricelist.getFields().put("datum_primene", "06/02/2016");
		row1Pricelist.getFields().put("preduzece", 1);
		TableRowDTO row2Pricelist = new TableRowDTO();
		row2Pricelist.getFields().put("id", 2);
		row2Pricelist.getFields().put("naziv", "Cenovnik 2");
		row2Pricelist.getFields().put("datum_primene", "05/03/2015");
		row2Pricelist.getFields().put("preduzece", 2);
		TableRowDTO row3Pricelist = new TableRowDTO();
		row3Pricelist.getFields().put("id", 3);
		row3Pricelist.getFields().put("naziv", "Cenovnik 3");
		row3Pricelist.getFields().put("datum_primene", "12/12/2015");
		row3Pricelist.getFields().put("preduzece", 2);
		rows1Pricelist.add(row1Pricelist);
		rows1Pricelist.add(row2Pricelist);
		rows1Pricelist.add(row3Pricelist);

		ArrayList<TableFieldDTO> fieldsPriceListItem = new ArrayList<TableFieldDTO>();
		fieldsPriceListItem.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsPriceListItem.add(new TableFieldDTO("cenovnik", false, true, "Cenovnik", "number"));
		fieldsPriceListItem.add(new TableFieldDTO("jedinicna_cena", false, false, null, "number"));
		fieldsPriceListItem.add(new TableFieldDTO("naziv_artikla", false, false, "Katalog", "text"));

		rows1PricelistItem = new ArrayList<TableRowDTO>();
		TableRowDTO row1PricelistItem = new TableRowDTO();
		row1PricelistItem.getFields().put("id", 1);
		row1PricelistItem.getFields().put("cenovnik", 1);
		row1PricelistItem.getFields().put("jedinicna_cena", "50");
		row1PricelistItem.getFields().put("naziv_artikla", "Artikal 1");
		TableRowDTO row2PricelistItem = new TableRowDTO();
		row2PricelistItem.getFields().put("id", 2);
		row2PricelistItem.getFields().put("cenovnik", 1);
		row2PricelistItem.getFields().put("jedinicna_cena", "100");
		row2PricelistItem.getFields().put("naziv_artikla", "Artikal 2");
		TableRowDTO row3PricelistItem = new TableRowDTO();
		row3PricelistItem.getFields().put("id", 3);
		row3PricelistItem.getFields().put("cenovnik", 2);
		row3PricelistItem.getFields().put("jedinicna_cena", "50");
		row3PricelistItem.getFields().put("naziv_artikla", "Artikal 3");
		TableRowDTO row4PricelistItem = new TableRowDTO();
		row4PricelistItem.getFields().put("id", 4);
		row4PricelistItem.getFields().put("cenovnik", 3);
		row4PricelistItem.getFields().put("jedinicna_cena", "200");
		row4PricelistItem.getFields().put("naziv_artikla", "Artikal 4");
		TableRowDTO row5PricelistItem = new TableRowDTO();
		row5PricelistItem.getFields().put("id", 5);
		row5PricelistItem.getFields().put("cenovnik", 1);
		row5PricelistItem.getFields().put("jedinicna_cena", "250");
		row5PricelistItem.getFields().put("naziv_artikla", "Artikal 5");
		TableRowDTO row6PricelistItem = new TableRowDTO();
		row6PricelistItem.getFields().put("id", 6);
		row6PricelistItem.getFields().put("cenovnik", 1);
		row6PricelistItem.getFields().put("jedinicna_cena", "99");
		row6PricelistItem.getFields().put("naziv_artikla", "Artikal 6");
		rows1PricelistItem.add(row1PricelistItem);
		rows1PricelistItem.add(row2PricelistItem);
		rows1PricelistItem.add(row3PricelistItem);
		rows1PricelistItem.add(row4PricelistItem);
		rows1PricelistItem.add(row5PricelistItem);
		rows1PricelistItem.add(row6PricelistItem);
		if (addedRowsPricelist.size() != 0) {
			for (TableRowDTO row : addedRowsPricelist) {
				rows1Pricelist.add(row);
			}
		}
		if (addedRowsPricelistItem.size() != 0) {
			for (TableRowDTO row : addedRowsPricelistItem) {
				rows1PricelistItem.add(row);
			}
		}

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

		tables.add(new TableDTO("Cenovnik", fieldsPriceList, rows1Pricelist, true,
				"Stavka cenovnika", null, null));
		tables.add(new TableDTO("Stavka cenovnika", fieldsPriceListItem, rows1PricelistItem, true, null,
				null, null));
		tables.add(new TableDTO("Preduzece", fieldsCompany, rows1Company, false,
				null, null, null));
		ArrayList<TableFieldDTO> catalogFields = new ArrayList<TableFieldDTO>();
		catalogFields.add(new TableFieldDTO("id", false, false, null, "number"));
		catalogFields.add(new TableFieldDTO("sifra_artikla", false, false, null, "number"));
		catalogFields.add(new TableFieldDTO("naziv_artikla", false, false, null, "text"));
		catalogFields.add(new TableFieldDTO("jedinicna_cena", false, false, null, "number"));

		ArrayList<TableRowDTO> rows1Catalog = new ArrayList<TableRowDTO>();
		TableRowDTO row1Catalog = new TableRowDTO();
		row1Catalog.getFields().put("id", 1);
		row1Catalog.getFields().put("sifra_artikla", "121");
		row1Catalog.getFields().put("naziv_artikla", "Artikal 1");
		row1Catalog.getFields().put("jedinicna_cena", "90");
		TableRowDTO row2Catalog = new TableRowDTO();
		row2Catalog.getFields().put("id", 2);
		row2Catalog.getFields().put("sifra_artikla", "122");
		row2Catalog.getFields().put("naziv_artikla", "Artikal 2");
		row2Catalog.getFields().put("jedinicna_cena", "320");
		TableRowDTO row4Catalog = new TableRowDTO();
		row4Catalog.getFields().put("id", 3);
		row4Catalog.getFields().put("sifra_artikla", "124");
		row4Catalog.getFields().put("naziv_artikla", "Artikal 4");
		row4Catalog.getFields().put("jedinicna_cena", "400");
		TableRowDTO row5Catalog = new TableRowDTO();
		row5Catalog.getFields().put("id", 5);
		row5Catalog.getFields().put("sifra_artikla", "125");
		row5Catalog.getFields().put("naziv_artikla", "Artikal 5");
		row5Catalog.getFields().put("jedinicna_cena", "180");
		TableRowDTO row3Catalog = new TableRowDTO();
		row3Catalog.getFields().put("id", 3);
		row3Catalog.getFields().put("sifra_artikla", "123");
		row3Catalog.getFields().put("naziv_artikla", "Artikal 3");
		row3Catalog.getFields().put("jedinicna_cena", "80");
		rows1Catalog.add(row1Catalog);
		rows1Catalog.add(row2Catalog);
		rows1Catalog.add(row3Catalog);
		rows1Catalog.add(row4Catalog);
		rows1Catalog.add(row5Catalog);

		ArrayList<String> cenovnikParents = new ArrayList<String>();
		cenovnikParents.add("Preduzece");
		ArrayList<String> preduzeceChildren = new ArrayList<String>();
		preduzeceChildren.add("Cenovnik");
		tables.add(new TableDTO("Cenovnik", fieldsPriceList, rows1Pricelist, true, "Stavka cenovnika", null, cenovnikParents));
		tables.add(new TableDTO("Stavka cenovnika", fieldsPriceListItem, rows1PricelistItem, true, null, null, null));
		tables.add(new TableDTO("Preduzece", fieldsCompany, rows1Company, false, null, preduzeceChildren, null));
		tables.add(new TableDTO("Katalog", catalogFields, rows1Catalog, false, null, null, null));
		*/
		return tables;
	}

}
