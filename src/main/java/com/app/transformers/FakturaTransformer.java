package com.app.transformers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;
import com.app.constants.AppConstants.DataTypes;
import com.app.constants.FieldNames;
import com.app.constants.TableNames;
import com.app.helpers.ConversionHelper;
import com.app.model.FakturaOtpremnica;
import com.app.model.Narudzba;
import com.app.model.PoslovnaGodina;
import com.app.model.PoslovniPartner;

public class FakturaTransformer implements ITransformer{

	private final String TIP_FAKTURE = "Tip";
	private final String DATUM_FAKTURE = "Datum fakture";
	private final String DATUM_VALUTE = "Datum valute";
	private final String DATUM_OBRACUNA = "Datum obračuna";
	private final String UKUPNO = "Ukupno";
	private final String RABAT = "Rabat";
	private final String POREZ = "Porez";
	private final String IZNOS = "Iznos";
	private final String TEKUCI_RACUN = "Tekući račun";
	private final String POZIV_NA_BROJ = "Poziv na broj";
	private final String STATUS = "Status";
	private final String DODATNE_NAPOMENE = "Dodatne napomene";
	private final String ADRESA_ISPORUKE = "Adresa isporuke";
	private final String BROJ_KAMIONA = "Broj kamiona";
	private final String PREVOZNIK = "Prevoznik";
	private final String IZDAO = "Izdao robu";
	private final String PREUZEO = "Preuzeo robu";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		FakturaOtpremnica c = (FakturaOtpremnica) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<FakturaOtpremnica> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		FakturaOtpremnica faktura = new FakturaOtpremnica();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		int broj;
		Date datum;
		
		broj = Integer.parseInt(rows.get(FieldNames.FAKTURA_OTPREMNICA_LOOKUP).toString());
		faktura.setFaBroj(broj);
		
		faktura.setFaTip(rows.get(TIP_FAKTURE).toString().charAt(0));
		
		datum = ConversionHelper.convertToDate(rows.get(DATUM_FAKTURE).toString());
		faktura.setFaDatum(datum);
		
		datum = ConversionHelper.convertToDate(rows.get(DATUM_VALUTE).toString());
		faktura.setFaDatumValute(datum);
		
		if(rows.get(DATUM_OBRACUNA) != null) {
			datum = ConversionHelper.convertToDate(rows.get(DATUM_OBRACUNA).toString());
			faktura.setFaDatumValute(datum);
		}		
		
		faktura.setFaTekracun(rows.get(TEKUCI_RACUN).toString());
		
		if(rows.get(POZIV_NA_BROJ) != null) {
			faktura.setFaPoziv(rows.get(POZIV_NA_BROJ).toString());
		}
		
		faktura.setStatusFakture(rows.get(STATUS).toString().charAt(0));
		
		if(rows.get(DODATNE_NAPOMENE) != null) {
			faktura.setDodatneNapomene(rows.get(DODATNE_NAPOMENE).toString());
		}
		
		if(rows.get(ADRESA_ISPORUKE) != null) {
			faktura.setAdresaIsporuke(rows.get(ADRESA_ISPORUKE).toString());
		}
		
		if(rows.get(BROJ_KAMIONA) != null) {
			faktura.setBrojKamiona(rows.get(BROJ_KAMIONA).toString());
		}
		
		if(rows.get(PREVOZNIK) != null) {
			faktura.setPrevoznik(rows.get(PREVOZNIK).toString());
		}
		
		if(rows.get(IZDAO) != null) {
			faktura.setRobuIRacunIzdao(rows.get(IZDAO).toString());
		}
		
		if(rows.get(PREUZEO) != null) {
			faktura.setRobuIRacunPreuzeo(rows.get(PREUZEO).toString());
		}
		
		faktura.setNarudzba((Narudzba)fks.get(TableNames.NARUDZBA));
		faktura.setPoslovnaGodina((PoslovnaGodina)fks.get(TableNames.POSLOVNA_GODINA));
		faktura.setPoslovniPartner((PoslovniPartner)fks.get(TableNames.POSLOVNI_PARTNER));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			faktura.setIdFaktureOtpremnice(id);
			//ako ima PK, onda je update => ostaviti vec izracunate vrednosti
			faktura.setFaUkupno(new BigDecimal(rows.get(UKUPNO).toString()));
			faktura.setFaRabat(new BigDecimal(rows.get(RABAT).toString()));
			faktura.setFaPorez(new BigDecimal(rows.get(POREZ).toString()));
			faktura.setFaIznos(new BigDecimal(rows.get(IZNOS).toString()));
		} else {
			//ako nema PK, onda je create => staviti nule
			faktura.setFaUkupno(new BigDecimal(0));
			faktura.setFaRabat(new BigDecimal(0));
			faktura.setFaPorez(new BigDecimal(0));
			faktura.setFaIznos(new BigDecimal(0));
		}
		
		return faktura;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new FakturaOtpremnica());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<FakturaOtpremnica> c) {
		for (FakturaOtpremnica artikal : c) {
			fillData(table, artikal);
		}		
	}

	private void fillData(TableDTO table, FakturaOtpremnica c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdFaktureOtpremnice());				
		fields.put(FieldNames.FAKTURA_OTPREMNICA_LOOKUP, c.getFaBroj());
		fields.put(TIP_FAKTURE, c.getFaTip());
		fields.put(DATUM_FAKTURE, c.getFaDatum());		
		fields.put(DATUM_VALUTE, c.getFaDatumValute());
		
		if(c.getDatumObracuna() != null) {
			fields.put(DATUM_OBRACUNA, c.getDatumObracuna());
		} else {
			fields.put(DATUM_OBRACUNA, "");
		}
		
		fields.put(UKUPNO, c.getFaUkupno());
		fields.put(RABAT, c.getFaRabat());
		fields.put(POREZ, c.getFaPorez());
		fields.put(IZNOS, c.getFaIznos());
		fields.put(TEKUCI_RACUN, c.getFaTekracun());
		
		if(c.getFaPoziv() != null) {
			fields.put(POZIV_NA_BROJ, c.getFaPoziv());
		} else {
			fields.put(POZIV_NA_BROJ, "");
		}
		
		fields.put(STATUS, c.getStatusFakture());
		
		if(c.getDodatneNapomene() != null) {
			fields.put(DODATNE_NAPOMENE, c.getDodatneNapomene());
		} else {
			fields.put(DODATNE_NAPOMENE, "");
		}
		
		if(c.getAdresaIsporuke() != null) {
			fields.put(ADRESA_ISPORUKE, c.getAdresaIsporuke());
		} else {
			fields.put(ADRESA_ISPORUKE, "");
		}
		
		if(c.getBrojKamiona() != null) {
			fields.put(BROJ_KAMIONA, c.getBrojKamiona());
		} else {
			fields.put(BROJ_KAMIONA, "");
		}
		
		if(c.getPrevoznik() != null) {
			fields.put(PREVOZNIK, c.getPrevoznik());
		} else {
			fields.put(PREVOZNIK, "");
		}
		
		if(c.getRobuIRacunIzdao() != null) {
			fields.put(IZDAO, c.getRobuIRacunIzdao());
		} else {
			fields.put(IZDAO, "");
		}
		
		if(c.getRobuIRacunPreuzeo() != null) {
			fields.put(PREUZEO, c.getRobuIRacunPreuzeo());
		} else {
			fields.put(PREUZEO, "");
		}
		
		
		if(c.getNarudzba() != null) {
			fields.put(TableNames.NARUDZBA, c.getNarudzba().getIdNarudzbe());
			fields.put(FieldNames.NARUDZBA_LOOKUP, c.getNarudzba().getBrojNarudzbe());
		} else {
			fields.put(TableNames.NARUDZBA, "");
			fields.put(FieldNames.NARUDZBA_LOOKUP, "");
		}

		fields.put(TableNames.POSLOVNA_GODINA, c.getPoslovnaGodina().getIdPoslovneGodine());
		fields.put(FieldNames.POSLOVNA_GODINA_LOOKUP, c.getPoslovnaGodina().getPgGodina2());
		
		fields.put(TableNames.POSLOVNI_PARTNER, c.getPoslovniPartner().getIdPartnerstva());
		fields.put(FieldNames.POSLOVNI_PARTNER_LOOKUP, c.getPoslovniPartner().getPreduzeceByIdPartnera().getIdPreduzeca());
		
		row.setFields(fields);
		row.setTableName(TableNames.FAKTURA_OTPREMNICA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.FAKTURA_OTPREMNICA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, FakturaOtpremnica jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.STAVKE_FAKTURE_OTPREMNICE);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.NARUDZBA);
		parents.add(TableNames.POSLOVNA_GODINA);
		parents.add(TableNames.POSLOVNI_PARTNER);
				
		table.setTableName(TableNames.FAKTURA_OTPREMNICA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.FAKTURA_OTPREMNICA));
		table.setDocumentPattern(true);
		table.setDocumentChildName(TableNames.STAVKE_FAKTURE_OTPREMNICE);		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, FakturaOtpremnica c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.FAKTURA_OTPREMNICA_LOOKUP, false, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(TIP_FAKTURE, false, false, false, false, "", DataTypes.CHAR);
		field.addEnumValue("P");
		field.addEnumValue("R");
		fields.add(field);
		field = new TableFieldDTO(DATUM_FAKTURE, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(DATUM_VALUTE, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(DATUM_OBRACUNA, false, true, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(UKUPNO, false, false, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);
		fields.add(field);
		field = new TableFieldDTO(RABAT, false, true, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);
		fields.add(field);
		field = new TableFieldDTO(POREZ, false, false, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);
		fields.add(field);
		field = new TableFieldDTO(IZNOS, false, false, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);
		fields.add(field);
		field = new TableFieldDTO(TEKUCI_RACUN, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(50);
		fields.add(field);
		field = new TableFieldDTO(POZIV_NA_BROJ, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(50);
		fields.add(field);
		field = new TableFieldDTO(STATUS, false, false, false, false, "", DataTypes.CHAR);
		field.addEnumValue("E");
		field.addEnumValue("O");
		field.addEnumValue("P");
		field.addEnumValue("S");
		field.setMaxLength(1);
		fields.add(field);
		field = new TableFieldDTO(DODATNE_NAPOMENE, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(200);
		fields.add(field);
		field = new TableFieldDTO(ADRESA_ISPORUKE, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(50);
		fields.add(field);
		field = new TableFieldDTO(BROJ_KAMIONA, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(10);
		fields.add(field);
		field = new TableFieldDTO(PREVOZNIK, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(50);
		fields.add(field);
		field = new TableFieldDTO(IZDAO, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(30);
		fields.add(field);
		field = new TableFieldDTO(PREUZEO, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(30);
		fields.add(field);
		
		
		field = new TableFieldDTO(TableNames.NARUDZBA, false, true, false, false, TableNames.NARUDZBA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.NARUDZBA_LOOKUP, false, true, false, true, TableNames.NARUDZBA, DataTypes.NUMBER);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.POSLOVNA_GODINA, false, false, false, false, TableNames.POSLOVNA_GODINA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POSLOVNA_GODINA_LOOKUP, false, false, false, true, TableNames.POSLOVNA_GODINA, DataTypes.NUMBER);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.POSLOVNI_PARTNER, false, false, false, false, TableNames.POSLOVNI_PARTNER, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POSLOVNI_PARTNER_LOOKUP, false, false, false, true, TableNames.POSLOVNI_PARTNER, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}
	
	private ArrayList<FakturaOtpremnica> castList(ArrayList<Object> entities) {
		ArrayList<FakturaOtpremnica> measures = new ArrayList<FakturaOtpremnica>();
		
		for (Object entity : entities) {
			measures.add((FakturaOtpremnica)entity);
		}
		return measures;
	}

}
