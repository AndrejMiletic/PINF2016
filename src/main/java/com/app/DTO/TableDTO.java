package com.app.DTO;

import java.util.ArrayList;

public class TableDTO {
	
	private String tableName;
	private String tableCode;
	private ArrayList<TableFieldDTO> fields; 
	private ArrayList<TableRowDTO> rows;
	private boolean documentPattern;
	private String documentChildName;
	private ArrayList<String> children;
	private ArrayList<String> parents;
	
	public TableDTO(){
		super();
		fields = new ArrayList<TableFieldDTO>();
		rows = new ArrayList<TableRowDTO>();
	}

	public TableDTO(String tableName, String tableCode, ArrayList<TableFieldDTO> fields,
			ArrayList<TableRowDTO> rows, boolean documentPattern,
			String documentChildName, ArrayList<String> children,
			ArrayList<String> parents) {
		super();
		this.tableName = tableName;
		this.fields = fields;
		this.rows = rows;
		this.documentPattern = documentPattern;
		this.documentChildName = documentChildName;
		this.children = children;
		this.parents = parents;
		this.tableCode = tableCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<TableFieldDTO> getFields() {
		return fields;
	}

	public void setFields(ArrayList<TableFieldDTO> fields) {
		this.fields = fields;
	}

	public ArrayList<TableRowDTO> getRows() {
		return rows;
	}

	public void setRows(ArrayList<TableRowDTO> rows) {
		this.rows = rows;
	}

	public boolean isDocumentPattern() {
		return documentPattern;
	}

	public void setDocumentPattern(boolean documentPattern) {
		this.documentPattern = documentPattern;
	}

	public String getDocumentChildName() {
		return documentChildName;
	}

	public void setDocumentChildName(String documentChildName) {
		this.documentChildName = documentChildName;
	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}

	public ArrayList<String> getParents() {
		return parents;
	}

	public void setParents(ArrayList<String> parents) {
		this.parents = parents;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
}
