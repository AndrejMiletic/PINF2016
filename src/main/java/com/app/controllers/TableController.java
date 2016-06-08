package com.app.controllers;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.PricelistDTO;
import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;

@RestController
@RequestMapping(value = "/table")
public class TableController {

	private ArrayList<TableRowDTO> rows1Pricelist;
	private ArrayList<TableRowDTO> rows1PricelistItem;

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
		ArrayList<String> ids = new ArrayList<String>();
		for (TableRowDTO row : rows1Pricelist) {
			ids.add(row.getFields().get("id").toString());
		}
		ArrayList<Integer> resultList = getIntegerArray(ids);
		int max = Collections.max(resultList);

		pricelist.getParent().getFields().put("naziv", "Cenovnik " + (max + 1));
		pricelist.getParent().getFields().put("id", (max + 1));
		rows1Pricelist.add(pricelist.getParent());
		for (TableRowDTO row : pricelist.getChild()) {
			rows1PricelistItem.add(row);
		}
		System.out.println(pricelist.getParent());
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

	@RequestMapping(value = "/getDocChild/{parentName}/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getDocChild(@PathVariable String parentName, @PathVariable String parentId) {
		Long id = Long.valueOf(parentId);
		ArrayList<TableDTO> tables = getMockData();
		TableDTO requestedTable = null;
		String name = null;
		for (TableDTO table : tables) {
			if (table.getTableName().equals(parentName)) {
				name = table.getDocumentChildName();
				break;
			}
		}
		for (TableDTO table : tables) {
			if (table.getTableName().equals(name)) {
				requestedTable = table;
				break;
			}
		}
		ArrayList<TableRowDTO> rows = new ArrayList<TableRowDTO>();
		for (int i = 0; i < requestedTable.getRows().size(); i++) {
			if (((Integer) requestedTable.getRows().get(i).getFields().get("parentId")) == id.longValue()) {
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
		fieldsPriceList.add(new TableFieldDTO("datum_primene", false, false, null, "date"));
		fieldsPriceList.add(new TableFieldDTO("preduzece", false, true, "Preduzece", "number"));

		rows1Pricelist = new ArrayList<TableRowDTO>();
		TableRowDTO row1Pricelist = new TableRowDTO();
		row1Pricelist.getFields().put("id", 1);
		row1Pricelist.getFields().put("naziv", "Cenovnik 1");
		row1Pricelist.getFields().put("datum_primene", "05.08.2015.");
		row1Pricelist.getFields().put("preduzece", "1");
		TableRowDTO row2Pricelist = new TableRowDTO();
		row2Pricelist.getFields().put("id", 2);
		row2Pricelist.getFields().put("naziv", "Cenovnik 2");
		row2Pricelist.getFields().put("datum_primene", "12.12.2015.");
		row2Pricelist.getFields().put("preduzece", "2");
		TableRowDTO row3Pricelist = new TableRowDTO();
		row3Pricelist.getFields().put("id", 3);
		row3Pricelist.getFields().put("naziv", "Cenovnik 3");
		row3Pricelist.getFields().put("datum_primene", "12.10.2015.");
		row3Pricelist.getFields().put("preduzece", "2");
		rows1Pricelist.add(row1Pricelist);
		rows1Pricelist.add(row2Pricelist);
		rows1Pricelist.add(row3Pricelist);

		ArrayList<TableFieldDTO> fieldsPriceListItem = new ArrayList<TableFieldDTO>();
		fieldsPriceListItem.add(new TableFieldDTO("id", false, false, null, "number"));
		fieldsPriceListItem.add(new TableFieldDTO("parentId", false, true, "Cenovnik", "number"));
		fieldsPriceListItem.add(new TableFieldDTO("id_artikla", false, true, "Katalog", "number"));
		fieldsPriceListItem.add(new TableFieldDTO("jedinicna_cena", false, false, null, "number"));		

		rows1PricelistItem = new ArrayList<TableRowDTO>();
		TableRowDTO row1PricelistItem = new TableRowDTO();
		row1PricelistItem.getFields().put("id", 1);
		row1PricelistItem.getFields().put("parentId", 1);		
		row1PricelistItem.getFields().put("id_artikla", "1");
		row1PricelistItem.getFields().put("jedinicna_cena", "50");
		TableRowDTO row2PricelistItem = new TableRowDTO();
		row2PricelistItem.getFields().put("id", 2);
		row2PricelistItem.getFields().put("parentId", 1);	
		row2PricelistItem.getFields().put("id_artikla", "2");
		row2PricelistItem.getFields().put("jedinicna_cena", "100");
		TableRowDTO row3PricelistItem = new TableRowDTO();
		row3PricelistItem.getFields().put("id", 3);
		row3PricelistItem.getFields().put("parentId", 2);		
		row3PricelistItem.getFields().put("id_artikla", "3");
		row3PricelistItem.getFields().put("jedinicna_cena", "150");
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
		
		ArrayList<TableFieldDTO> catalogFields = new ArrayList<TableFieldDTO>();
		catalogFields.add(new TableFieldDTO("id_artikla", false, false, null, "number"));
		catalogFields.add(new TableFieldDTO("sifra_artikla", false, false, null, "number"));
		catalogFields.add(new TableFieldDTO("naziv_artikla", false, false, null, "text"));

		ArrayList<TableRowDTO> rows1Catalog = new ArrayList<TableRowDTO>();
		TableRowDTO row1Catalog = new TableRowDTO();
		row1Catalog.getFields().put("id_artikla", 1);
		row1Catalog.getFields().put("sifra_artikla", "121");
		row1Catalog.getFields().put("naziv_artikla", "Cokolada");
		TableRowDTO row2Catalog = new TableRowDTO();
		row2Catalog.getFields().put("id_artikla", 2);
		row2Catalog.getFields().put("sifra_artikla", "122");
		row2Catalog.getFields().put("naziv_artikla", "Meso");
		TableRowDTO row3Catalog = new TableRowDTO();
		row3Catalog.getFields().put("id_artikla", 3);
		row3Catalog.getFields().put("sifra_artikla", "123");
		row3Catalog.getFields().put("naziv_artikla", "Mleko");
		rows1Catalog.add(row1Catalog);
		rows1Catalog.add(row2Catalog);
		rows1Catalog.add(row3Catalog);

		tables.add(new TableDTO("Faktura", fields1, rows1, true, "Stavka fakture", null, null));
		tables.add(new TableDTO("Stavka fakture", fields2, rows2, true, null, null, null));

		tables.add(new TableDTO("Cenovnik", fieldsPriceList, rows1Pricelist, true, "Stavka cenovnika", null, null));
		tables.add(new TableDTO("Stavka cenovnika", fieldsPriceListItem, rows1PricelistItem, true, null, null, null));
		tables.add(new TableDTO("Preduzece", fieldsCompany, rows1Company, false, null, null, null));
		tables.add(new TableDTO("Katalog", catalogFields, rows1Catalog, false, null, null, null));
		return tables;
	}
}
