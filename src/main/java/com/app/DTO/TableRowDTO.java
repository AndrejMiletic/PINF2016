package com.app.DTO;

import java.util.HashMap;

public class TableRowDTO {

	private HashMap<String, Object> fields;

	public TableRowDTO() {
		super();
		fields = new HashMap<String, Object>();
	}

	public TableRowDTO(HashMap<String, Object> fields) {
		super();
		this.fields = fields;
	}

	public HashMap<String, Object> getFields() {
		return fields;
	}

	public void setFields(HashMap<String, Object> fields) {
		this.fields = fields;
	}
}
