package com.app.DTO;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TableRowDTO {

	private String tableName;
	private String tableCode;
	private LinkedHashMap<String, Object> fields;

	public TableRowDTO() {
		super();
		fields = new LinkedHashMap<String, Object>();
	}

	public TableRowDTO(LinkedHashMap<String, Object> fields) {
		super();
		this.fields = fields;
	}

	public TableRowDTO(String tableName, String tableCode, LinkedHashMap<String, Object> fields) {
		super();
		this.tableName = tableName;
		this.fields = fields;
		this.tableCode = tableCode;
	}

	public HashMap<String, Object> getFields() {
		return fields;
	}

	public void setFields(LinkedHashMap<String, Object> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		String retVal = "";
		// TODO Auto-generated method stub
		for (String key : fields.keySet()) {
			retVal += key + " : "+ fields.get(key)+"\n"; 
		}
		return retVal;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
}
