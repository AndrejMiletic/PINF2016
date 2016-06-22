package com.app.DTO;

import java.util.ArrayList;

import com.app.constants.AppConstants.DataTypes;

public class TableFieldDTO {

	private String name;
	private boolean pk;
	private boolean nullable;
	private boolean fk;
	private boolean lookup;
	private String fkTableName;
	private DataTypes type;
	
	private boolean calculated;
	private int maxLength;
	
	private boolean enumeration;
	private ArrayList<String> enumValues;
	
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
		this.calculated = false;
		this.maxLength = -1;
		this.enumeration = false;
		this.enumValues = new ArrayList<String>();
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
	public boolean isCalculated() {
		return calculated;
	}
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public boolean isEnumeration() {
		return enumeration;
	}
	public void setEnumeration(boolean enumeration) {
		this.enumeration = enumeration;
	}
	public ArrayList<String> getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(ArrayList<String> enumValues) {
		this.enumValues = enumValues;
	}
	public void addEnumValue(String val) {
		this.enumeration = true;
		this.enumValues.add(val);
	}
}
