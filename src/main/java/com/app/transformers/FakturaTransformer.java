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

	private final String TIP_FAKTURE = "Šifra artikla";
	private final String DATUM_FAKTURE = "Šifra artikla";
	private final String DATUM_VALUTE = "Šifra artikla";
	private final String DATUM_OBRACUNA = "Šifra artikla";
	private final String UKUPNO = "Šifra artikla";
	private final String RABAT = "Šifra artikla";
	private final String POREZ = "Šifra artikla";
	private final String IZNOS = "Šifra artikla";
	private final String TEKUCI_RACUN = "Šifra artikla";
	private final String POZIV_NA_BROJ = "Šifra artikla";
	private final String STATUS = "Šifra artikla";
	private final String DODATNE_NAPOMENE = "Šifra artikla";
	private final String ADRESA_ISPORUKE = "Šifra artikla";
	private final String BROJ_KAMIONA = "Šifra artikla";
	private final String PREVOZNIK = "Šifra artikla";
	private final String IZDAO = "Šifra artikla";
	private final String PREUZEO = "Šifra artikla";
	
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
		
		faktura.setFaUkupno(new BigDecimal(0));
		faktura.setFaRabat(new BigDecimal(0));
		faktura.setFaPorez(new BigDecimal(0));
		faktura.setFaIznos(new BigDecimal(0));
		
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
		/*
		fields.put(FieldNames.PRIMARY_KEY, c.getIdArtikla());				
		fields.put(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, c.getNazivArtikla());
		fields.put(SIFRA_ARTIKLA, c.getSifraArtikla());		

		fields.put(TableNames.GRUPA_PROIZVODA, c.getGrupaProizvoda().getIdGrupe());
		fields.put(FieldNames.GRUPA_PROIZVODA_LOOKUP, c.getGrupaProizvoda().getNazivGrupe());		

		fields.put(TableNames.JEDINICE_MERE, c.getJediniceMere().getIdJediniceMere());
		fields.put(FieldNames.JEDINICA_MERE_LOOKUP, c.getJediniceMere().getNazivJediniceMere());
		*/
		row.setFields(fields);
		row.setTableName(TableNames.KATALOG_ROBE_I_USLUGA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.KATALOG_ROBE_I_USLUGA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, FakturaOtpremnica jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.STAVKE_FAKTURE_OTPREMNICE);
		children.add(TableNames.STAVKE_NARUDZBE);
		children.add(TableNames.STAVKE_CENOVNIKA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.JEDINICE_MERE);
		parents.add(TableNames.GRUPA_PROIZVODA);
				
		table.setTableName(TableNames.KATALOG_ROBE_I_USLUGA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.KATALOG_ROBE_I_USLUGA));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, FakturaOtpremnica c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		//field = new TableFieldDTO(SIFRA_ARTIKLA, false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.JEDINICE_MERE, false, false, false, false, TableNames.JEDINICE_MERE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.JEDINICA_MERE_LOOKUP, false, false, false, true, TableNames.JEDINICE_MERE, DataTypes.TEXT);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.GRUPA_PROIZVODA, false, false, false, false, TableNames.GRUPA_PROIZVODA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.GRUPA_PROIZVODA_LOOKUP, false, false, false, true, TableNames.GRUPA_PROIZVODA, DataTypes.TEXT);
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
