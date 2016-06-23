package com.app.transformers;

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
import com.app.model.Narudzba;
import com.app.model.PoslovnaGodina;
import com.app.model.PoslovniPartner;

public class NarudzbaTransformer implements ITransformer {


	private final String DATUM_NARUCIVANJA = "Datum naručivanja";
	private final String ROK_ISPORUKE = "Rok isporuke";
	private final String NACIN_OTPREME = "Način otpreme";
	private final String STATUS_NARUDZBE = "Status";
	private final String NACIN_PLACANJA = "Način plaćanja";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		Narudzba c = (Narudzba) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<Narudzba> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;	
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		Narudzba narudzba = new Narudzba();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		int brojNarudzbe;
		Date datum;
		
		brojNarudzbe = Integer.parseInt(rows.get(FieldNames.NARUDZBA_LOOKUP).toString());
		narudzba.setBrojNarudzbe(brojNarudzbe);
		
		if(rows.containsKey(NACIN_OTPREME)) {
			narudzba.setNacinOtpreme(rows.get(NACIN_OTPREME).toString());
		}
		
		if(rows.containsKey(NACIN_PLACANJA)) {
			narudzba.setNacinPlacanja(rows.get(NACIN_PLACANJA).toString());
		}
		
		datum = ConversionHelper.convertToDate(rows.get(DATUM_NARUCIVANJA).toString());
		narudzba.setDatumNarucivanja(datum);
		
		datum = ConversionHelper.convertToDate(rows.get(ROK_ISPORUKE).toString());
		narudzba.setRokIsporuke(datum);
		
		narudzba.setStatusNarudzbe(rows.get(STATUS_NARUDZBE).toString().charAt(0));
		
		narudzba.setPoslovnaGodina((PoslovnaGodina)fks.get(TableNames.POSLOVNA_GODINA));
		
		if(fks.containsKey(TableNames.POSLOVNI_PARTNER)) {
			narudzba.setPoslovniPartner((PoslovniPartner)fks.get(TableNames.POSLOVNI_PARTNER));
		}
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			narudzba.setIdNarudzbe(id);
		}
		
		return narudzba;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new Narudzba());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<Narudzba> c) {
		for (Narudzba artikal : c) {
			fillData(table, artikal);
		}		
	}

	private void fillData(TableDTO table, Narudzba c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdNarudzbe());				
		fields.put(FieldNames.NARUDZBA_LOOKUP, c.getBrojNarudzbe());
		fields.put(DATUM_NARUCIVANJA, c.getDatumNarucivanja());
		fields.put(ROK_ISPORUKE, c.getRokIsporuke());
		
		if(c.getNacinOtpreme() != null) {
			fields.put(NACIN_OTPREME, c.getNacinOtpreme());
		} else {
			fields.put(NACIN_OTPREME, "");
		}
		
		if(c.getNacinPlacanja() != null) {
			fields.put(NACIN_PLACANJA, c.getNacinPlacanja());
		} else {
			fields.put(NACIN_PLACANJA, "");
		}
		
		fields.put(STATUS_NARUDZBE, c.getStatusNarudzbe());

		fields.put(TableNames.POSLOVNA_GODINA, c.getPoslovnaGodina().getIdPoslovneGodine());
		fields.put(FieldNames.POSLOVNA_GODINA_LOOKUP, c.getPoslovnaGodina().getPgGodina2());		

		if(c.getPoslovniPartner() != null) {		
			//uzima ID preduzeca koji je partner
			fields.put(TableNames.POSLOVNI_PARTNER, c.getPoslovniPartner().getIdPartnerstva());
			//uzima naziv preduzeca koji je partner
			fields.put(FieldNames.POSLOVNI_PARTNER_LOOKUP, c.getPoslovniPartner().getPreduzeceByIdPartnera().getNaziv());
		} else {
			//uzima ID preduzeca koji je partner
			fields.put(TableNames.POSLOVNI_PARTNER, "");
			//uzima naziv preduzeca koji je partner
			fields.put(FieldNames.POSLOVNI_PARTNER_LOOKUP, "");
		}
		
		row.setFields(fields);
		row.setTableName(TableNames.NARUDZBA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.NARUDZBA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, Narudzba jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.FAKTURA_OTPREMNICA);
		children.add(TableNames.STAVKE_NARUDZBE);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.POSLOVNA_GODINA);
		parents.add(TableNames.POSLOVNI_PARTNER);
				
		table.setTableName(TableNames.NARUDZBA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.NARUDZBA));
		table.setDocumentPattern(true);
		table.setDocumentChildName(TableNames.STAVKE_NARUDZBE);		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, Narudzba c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.NARUDZBA_LOOKUP, false, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(DATUM_NARUCIVANJA, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(ROK_ISPORUKE, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(NACIN_OTPREME, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(30);
		fields.add(field);
		field = new TableFieldDTO(NACIN_PLACANJA, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(30);
		fields.add(field);
		field = new TableFieldDTO(STATUS_NARUDZBE, false, false, false, false, "", DataTypes.CHAR);
		field.addEnumValue("E");
		field.addEnumValue("P");
		field.addEnumValue("S");
		field.setMaxLength(1);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.POSLOVNA_GODINA, false, false, true, false, TableNames.POSLOVNA_GODINA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POSLOVNA_GODINA_LOOKUP, false, false, false, true, TableNames.POSLOVNA_GODINA, DataTypes.NUMBER);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.POSLOVNI_PARTNER, false, true, true, false, TableNames.POSLOVNI_PARTNER, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POSLOVNI_PARTNER_LOOKUP, false, true, false, true, TableNames.POSLOVNI_PARTNER, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}
	
	private ArrayList<Narudzba> castList(ArrayList<Object> entities) {
		ArrayList<Narudzba> measures = new ArrayList<Narudzba>();
		
		for (Object entity : entities) {
			measures.add((Narudzba)entity);
		}
		return measures;
	}



}
