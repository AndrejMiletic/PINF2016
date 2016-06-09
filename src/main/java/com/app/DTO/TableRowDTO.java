package com.app.DTO;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TableRowDTO {

	private String tableName;
	private LinkedHashMap<String, Object> fields;

	public TableRowDTO() {
		super();
		fields = new LinkedHashMap<String, Object>();
	}

	public TableRowDTO(LinkedHashMap<String, Object> fields) {
		super();
		this.fields = fields;
	}

	public TableRowDTO(String tableName, LinkedHashMap<String, Object> fields) {
		super();
		this.tableName = tableName;
		this.fields = fields;
	}

	public HashMap<String, Object> getFields() {
		return fields;
	}

	public void setFields(LinkedHashMap<String, Object> fields) {
		this.fields = fields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
