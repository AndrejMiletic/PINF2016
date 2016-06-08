package com.app.DTO;

public class TableFieldDTO {

	private String name;
	private boolean nullable;
	private boolean fk;
	private String fkTableName;
	private String type;
	
	public TableFieldDTO() {
		super();
	}
	public TableFieldDTO(String name, boolean nullable, boolean fk,
			String fkTableName, String type) {
		super();
		this.type = type;
		this.name = name;
		this.nullable = nullable;
		this.fk = fk;
		this.fkTableName = fkTableName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
