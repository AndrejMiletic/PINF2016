package com.app.DTO;

import java.util.HashMap;

public class TableRowDTO {

	private String tableName;
	private HashMap<String, Object> fields;

	public TableRowDTO() {
		super();
		fields = new HashMap<String, Object>();
	}

	public TableRowDTO(HashMap<String, Object> fields) {
		super();
		this.fields = fields;
	}

	public TableRowDTO(String tableName, HashMap<String, Object> fields) {
		super();
		this.tableName = tableName;
		this.fields = fields;
	}

	public HashMap<String, Object> getFields() {
		return fields;
	}

	public void setFields(HashMap<String, Object> fields) {
		this.fields = fields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
