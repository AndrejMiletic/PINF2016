package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.invoice.FakturaOtpremnica;
import com.app.repositories.XHTML_PDF_Repository;

@Component
public class GenerateService {

	@Autowired
	private XHTML_PDF_Repository repo;
	
	public boolean generateXML(FakturaOtpremnica obj, String filePath){
		if(repo.generateXml(obj, filePath))
			return true;
		return false;
	}
	
}
