package com.app.DTO;

import com.app.constants.AppConstants.DataTypes;

public class TableFieldDTO {

	private String name;
	private boolean pk;
	private boolean nullable;
	private boolean fk;
	private boolean lookup;
	private String fkTableName;
	private DataTypes type;
	
	public TableFieldDTO() {
		super();
	}
	public TableFieldDTO(String name, boolean pk, boolean nullable, boolean fk, boolean lookup,	String fkTableName, DataTypes type) {
		super();
		this.type = type;
		this.name = name;
		this.nullable = nullable;
		this.fk = fk;
		this.fkTableName = fkTableName;
		this.lookup = lookup;
		this.pk = pk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isFk() {
		return fk;
	}
	public void setFk(boolean fk) {
		this.fk = fk;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}
	public DataTypes getType() {
		return type;
	}
	public void setType(DataTypes type) {
		this.type = type;
	}
	public boolean isLookup() {
		return lookup;
	}
	public void setLookup(boolean lookup) {
		this.lookup = lookup;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
}
