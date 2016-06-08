package com.app.DTO;

import java.util.ArrayList;

public class PricelistDTO {
	
	private TableRowDTO parent;
	private ArrayList<TableRowDTO> child;
	
	public PricelistDTO() {
		// TODO Auto-generated constructor stub
	}

	public TableRowDTO getParent() {
		return parent;
	}

	public void setParent(TableRowDTO parent) {
		this.parent = parent;
	}

	public ArrayList<TableRowDTO> getChild() {
		return child;
	}

	public void setChild(ArrayList<TableRowDTO> child) {
		this.child = child;
	}


	
}
