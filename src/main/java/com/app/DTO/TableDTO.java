package com.app.DTO;

import java.util.ArrayList;

public class TableDTO {
	
	private String tableName;
	private Long id;
	private ArrayList<TableFieldDTO> fields; 
	private ArrayList<TableRowDTO> rows;
	private boolean documentPattern;
	private String documentChildName;
	private ArrayList<String> children;
	private ArrayList<String> parents;
	
	public TableDTO(){
		super();
	}
}
