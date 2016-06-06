package com.app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	private ArrayList<TableDTO> getMockData(){
		ArrayList<TableDTO> tables = new ArrayList<TableDTO>();
		
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		fields.add(new TableFieldDTO("naziv", false, false, null));
		fields.add(new TableFieldDTO("tip", false, false, null));
		fields.add(new TableFieldDTO("godina", false, true, "Poslovna godina"));
		
		ArrayList<TableRowDTO> rows1 = new ArrayList<TableRowDTO>();
		TableRowDTO row11 = new TableRowDTO();
		row11.getFields().add("Faktura 1");
		row11.getFields().add("Prvi tip fakture");
		row11.getFields().add("Godina 1");
		TableRowDTO row12 = new TableRowDTO();
		row12.getFields().add("Faktura 2");
		row12.getFields().add("Drugi tip fakture");
		row12.getFields().add("Godina 2");
		rows1.add(row11);
		rows1.add(row12);
		
		tables.add(new TableDTO("Faktura", (long) 2, fields , rows1, true, "Stavka fakture", null, null));
		
		ArrayList<TableRowDTO> rows2 = new ArrayList<TableRowDTO>();
		TableRowDTO row21 = new TableRowDTO();
		row21.getFields().add("Faktura 1");
		row21.getFields().add("Prvi tip fakture");
		row21.getFields().add("Godina 1");
		TableRowDTO row22 = new TableRowDTO();
		row22.getFields().add("Faktura 2");
		row22.getFields().add("Drugi tip fakture");
		row22.getFields().add("Godina 2");
		rows2.add(row21);
		rows2.add(row22);
		
		tables.add(new TableDTO("Faktura", (long) 1, fields , rows2, true, "Stavka fakture", null, null));
		
		return tables;
	}
}
