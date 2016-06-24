
package com.app.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;
import com.app.constants.AppConstants.DataTypes;
import com.app.constants.FieldNames;
import com.app.constants.TableNames;
import com.app.helpers.ConversionHelper;
import com.app.model.Cenovnik;
import com.app.model.Preduzece;
import com.app.model.SifraDelatnosti;

public class PreduzeceTransformer implements ITransformer {
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		Preduzece p = (Preduzece) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, p);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, p);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<Preduzece> p = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, p.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, p);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		Preduzece p = new Preduzece();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
			
		p.setSifraDelatnosti((SifraDelatnosti)fks.get(TableNames.SIFRA_DELATNOSTI));
	
		if(rows.containsKey(FieldNames.PREDUZECE_LOOKUP)) {
			p.setNaziv(rows.get(FieldNames.PREDUZECE_LOOKUP).toString());
		}
		if(rows.containsKey("PIB")) {
			p.setPib(rows.get("PIB").toString());
		}
		if(rows.containsKey("Matični broj")) {
			p.setMaticniBroj(rows.get("Matični broj").toString());
		}
		if(rows.containsKey("Adresa")) {
			p.setAdresa(rows.get("Adresa").toString());
		}
		if(rows.containsKey("Broj telefona")) {
			p.setBrojTelefona(rows.get("Broj telefona").toString());
		}
		if(rows.containsKey("Email")) {
			p.setEmail(rows.get("Email").toString());
		}
		if(rows.containsKey("Banka")) {
			p.setBanka(rows.get("Banka").toString());
		}
		if(rows.containsKey("Tekući račun")) {
			p.setTekuciRacun(rows.get("Tekući račun").toString());
		}
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			p.setIdPreduzeca(id);
		}
		
		return p;
	}
	
	private void fillData(TableDTO table, ArrayList<Preduzece> p) {
		for (Preduzece pred: p) {
			fillData(table, pred);
		}		
	}
	
	private void fillData(TableDTO table, Preduzece p) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put("Id", p.getIdPreduzeca());
			
		fields.put(FieldNames.PREDUZECE_LOOKUP,p.getNaziv());
		fields.put("PIB", p.getPib());
		fields.put("Matični broj", p.getMaticniBroj());
		
		if(p.getAdresa() == null ||p.getAdresa().equals("")) {
			fields.put("Adresa", "");
		} else {
			fields.put("Adresa", p.getAdresa());
		}
		if(p.getBrojTelefona() == null ||p.getBrojTelefona().equals("")) {
			fields.put("Broj telefona", "");
		} else {
			fields.put("Broj telefona", p.getBrojTelefona());
		}
		if(p.getEmail() == null ||p.getEmail().equals("")) {
			fields.put("Email", "");
		} else {
			fields.put("Email", p.getEmail());
		}
		if(p.getBanka() == null ||p.getBanka().equals("")) {
			fields.put("Banka", "");
		} else {
			fields.put("Banka", p.getBanka());
		}		
		fields.put("Tekući račun", p.getTekuciRacun());
		
		fields.put(TableNames.SIFRA_DELATNOSTI, p.getSifraDelatnosti().getIdSifreDelatnosti());
		fields.put(FieldNames.SIFRA_DELATNOSTI_LOOKUP, p.getSifraDelatnosti().getNazivSifreDelatnosti());
		
		row.setFields(fields);
		row.setTableName(TableNames.PREDUZECE);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.PREDUZECE));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, Preduzece p) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.GRUPA_PROIZVODA);
		children.add(TableNames.CENOVNIK);
		children.add(TableNames.POSLOVNA_GODINA);
		children.add(TableNames.POSLOVNI_PARTNER);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.SIFRA_DELATNOSTI);
				
		table.setTableName(TableNames.PREDUZECE);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.PREDUZECE));
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, p);
	}
	
	private void fillFields(TableDTO table, Preduzece p) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO("PIB", false, false, false, false, "", DataTypes.CHAR);
		field.setMaxLength(9);
		fields.add(field);
		field = new TableFieldDTO("Matični broj", false, false, false, false, "", DataTypes.CHAR);
		field.setMaxLength(8);
		fields.add(field);
		field = new TableFieldDTO("Adresa", false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO("Broj telefona", false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(20);
		fields.add(field);
		field = new TableFieldDTO("Email", false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO("Banka", false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO("Tekući račun", false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(TableNames.SIFRA_DELATNOSTI, false, false, true, false, TableNames.SIFRA_DELATNOSTI, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.SIFRA_DELATNOSTI_LOOKUP, false, false, false, true, TableNames.SIFRA_DELATNOSTI, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}


	private ArrayList<Preduzece> castList(ArrayList<Object> entities) {
		ArrayList<Preduzece> menus = new ArrayList<Preduzece>();
		
		for (Object entity : entities) {
			menus.add((Preduzece)entity);
		}
		return menus;
	}

	@Override
	public TableDTO getMetaData() {
	TableDTO table = new TableDTO();
		
		fillMetaData(table, new Preduzece());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}

}
