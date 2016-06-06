package com.app.DTO;

import java.util.ArrayList;

public class TableRowDTO {

	private ArrayList<Object> fields;

	public TableRowDTO() {
		super();
		fields = new ArrayList<Object>();
	}

	public TableRowDTO(ArrayList<Object> fields) {
		super();
		this.fields = fields;
	}

	public ArrayList<Object> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Object> fields) {
		this.fields = fields;
	}
}
