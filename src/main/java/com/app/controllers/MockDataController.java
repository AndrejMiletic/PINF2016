package com.app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;

@RestController
@RequestMapping(value="/table")
public class MockDataController {

	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getAllUsers()
	{	
		ArrayList<TableDTO> tables = getMockData();
		ArrayList<String> names = new ArrayList<String>();
		for (TableDTO table : tables){
			if (!names.contains(table.getTableName())) names.add(table.getTableName());
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
	
	private ArrayList<TableDTO> getMockData(){
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();
		
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		fields.add(new TableFieldDTO("id", false, false, null));
		fields.add(new TableFieldDTO("naziv", false, false, null));
		fields.add(new TableFieldDTO("tip", false, false, null));
		fields.add(new TableFieldDTO("godina", false, true, "Poslovna godina"));
		
		ArrayList<TableRowDTO> rows1 = new ArrayList<TableRowDTO>();
		TableRowDTO row11 = new TableRowDTO();
		row11.getFields().add(1);
		row11.getFields().add("Faktura 1");
		row11.getFields().add("Prvi tip fakture");
		row11.getFields().add("Godina 1");
		TableRowDTO row12 = new TableRowDTO();
		row12.getFields().add(2);
		row12.getFields().add("Faktura 2");
		row12.getFields().add("Drugi tip fakture");
		row12.getFields().add("Godina 2");
		TableRowDTO row13 = new TableRowDTO();
		row13.getFields().add(3);
		row13.getFields().add("Faktura 3");
		row13.getFields().add("Drugi tip fakture");
		row13.getFields().add("Godina 1");
		rows1.add(row11);
		rows1.add(row12);
		rows1.add(row13);
		
		tables.add(new TableDTO("Faktura", fields , rows1, true, "Stavka fakture", null, null));
		
		return tables;
	}
}
