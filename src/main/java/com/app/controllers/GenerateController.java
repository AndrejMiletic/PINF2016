package com.app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.invoice.FakturaOtpremnica;
import com.app.model.invoice.StavkaFaktureOtpremnice;
import com.app.model.invoice.StavkaFaktureOtpremnice.Artikal;
import com.app.services.GenerateService;

@RestController
@RequestMapping(value = "/generate")
public class GenerateController {
	
	@Autowired
	private GenerateService service;

	@RequestMapping(path = "/xml", method = RequestMethod.GET)
	public ResponseEntity<Object> generateXMl() {
		String filePath="src/main/resources/faktura.xml";
		FakturaOtpremnica obj=fakturaMock();
		if(service.generateXML(obj, filePath)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	public FakturaOtpremnica fakturaMock(){
		FakturaOtpremnica faktura=new FakturaOtpremnica();
		
		faktura.setAdresaIsporuke("Adresa1");
		faktura.setBrojKamiona("kamion 1");
		faktura.setDodatneNapomene("dodatne napomene");
		faktura.setFaBroj(5);
		faktura.setFaDatum("28.5.2015");
		faktura.setFaDatumObracuna("3.3.2016.");
		faktura.setFaDatumValute("3.3.2016.");
		faktura.setFaIznos(new BigDecimal(96));
		faktura.setFaPorez(new BigDecimal(86));
		faktura.setFaPoziv("poziv na broj");
		faktura.setFaRabat(new BigDecimal(86));
		faktura.setFaTekuciracun("tekuci racun");
		faktura.setFaTip("Tip fakture");
		faktura.setFaUkupno(new BigDecimal(86));
		faktura.setIdFaktureOtpremnice(1l);
		faktura.setIdNarudzbe(1l);
		faktura.setIdPartnerstva(1l);
		faktura.setIdPoslovneGodine(1l);
		faktura.setPoslovnaGodina(new Short("5"));
		faktura.setPrevoznik("prevoznik");
		faktura.setRobuIRacunIzdao("robu i racun izdao");
		faktura.setRobuIRacunPreuzeo("robu i racun izdao");
		faktura.setStatusFakture("status");
		
		Artikal artikal1=new Artikal();
		artikal1.setIdArtikla(1l);
		artikal1.setNazivArtikla("Artikal1");
		artikal1.setSifraArtikla("545468454545");
		
		StavkaFaktureOtpremnice stavka1=new StavkaFaktureOtpremnice();
		stavka1.setArtikal(artikal1);
		stavka1.setIdFaktureOtpremnice(1l);
		stavka1.setIdStavkeFakture(1l);
		stavka1.setJedinicnaCenaStavkeFakture(new BigDecimal(86));
		stavka1.setKolicina(new BigDecimal(86));
		stavka1.setOsnovicaPDV(new BigDecimal(86));
		stavka1.setRabat(new BigDecimal(86));
		
		StavkaFaktureOtpremnice stavka2=new StavkaFaktureOtpremnice();
		stavka2.setArtikal(artikal1);
		stavka2.setIdFaktureOtpremnice(1l);
		stavka2.setIdStavkeFakture(2l);
		stavka2.setJedinicnaCenaStavkeFakture(new BigDecimal(86));
		stavka2.setKolicina(new BigDecimal(86));
		stavka2.setOsnovicaPDV(new BigDecimal(86));
		stavka2.setRabat(new BigDecimal(86));
		
		List<StavkaFaktureOtpremnice> stavke=new ArrayList<>();
		stavke.add(stavka1);
		stavke.add(stavka2);
		
		faktura.getStavkaFaktureOtpremnice().addAll(stavke);
		
		return faktura;
	}
	
}
