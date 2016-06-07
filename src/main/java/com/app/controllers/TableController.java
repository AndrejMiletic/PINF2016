package com.app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
	public ResponseEntity<TableDTO> getByName(@PathVariable String name) {
		ArrayList<TableDTO> tables = getMockData();
		TableDTO requestedTable = null;
		for (TableDTO table : tables) {
			if (table.getTableName().equals(name)) {
				requestedTable = table;
				break;
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
	/*	for (int i=0; i < requestedTable.getRows().size(); i++){
			if (requestedTable.getRows().get(i).g
		}*/
		return null;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseEntity<Object> addTable(@RequestBody TableDTO table){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private ArrayList<TableDTO> getMockData() {
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();

		ArrayList<TableFieldDTO> fields1 = new ArrayList<TableFieldDTO>();
		fields1.add(new TableFieldDTO("id", false, false, null));
		fields1.add(new TableFieldDTO("naziv", false, false, null));
		fields1.add(new TableFieldDTO("tip", false, false, null));
		fields1.add(new TableFieldDTO("godina", false, true, "Poslovna godina"));

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
		fields2.add(new TableFieldDTO("id", false, false, null));
		fields2.add(new TableFieldDTO("parentID", false, true, "Faktura"));
		fields2.add(new TableFieldDTO("vrednost", false, false, null));

		ArrayList<TableRowDTO> rows2 = new ArrayList<TableRowDTO>();
		TableRowDTO row21 = new TableRowDTO();
		row21.getFields().put("id", 1);
		row21.getFields().put("parentId", 1);
		row21.getFields().put("vrednost", "Stavka 1 od f1");
		TableRowDTO row22 = new TableRowDTO();
		row22.getFields().put("id", 2);
		row22.getFields().put("parentId", 1);
		row22.getFields().put("vrednost", "Stavka 2 od f2");
		TableRowDTO row23 = new TableRowDTO();
		row23.getFields().put("id", 3);
		row23.getFields().put("parentId", 2);
		row23.getFields().put("vrednost", "Stavka 1 od f2");
		rows2.add(row21);
		rows2.add(row22);
		rows2.add(row23);

		tables.add(new TableDTO("Faktura", fields1, rows1, true,
				"Stavka fakture", null, null));
		tables.add(new TableDTO("Stavka fakture", fields2, rows2, true, null,
				null, null));
		return tables;
	}
}
