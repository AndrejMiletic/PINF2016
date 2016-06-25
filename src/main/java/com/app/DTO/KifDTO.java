package com.app.DTO;

public class KifDTO {

	private String dateFrom;
	private String dateTo;
	private Long companyId;
	private String companyName;
	
	public KifDTO() {
	}
			
	public KifDTO(String dateFrom, String dateTo, Long companyId, String companyName) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
