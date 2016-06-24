package com.app.repositories;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Repository;

import com.app.model.invoice.Faktura;

@Repository
public class XML_Repository {

	public boolean generateXml(Faktura obj, String filePath){

		try{
				// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
				JAXBContext context = JAXBContext.newInstance(Faktura.class);

				// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
				Marshaller marshaller = context.createMarshaller();
				
				// Podešavanje marshaller-a
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				// Umesto System.out-a, može se koristiti FileOutputStream
				marshaller.marshal(obj, new File(filePath));

				return true;
				
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		
		return false;
	}
	
}
