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
import com.app.model.SifraDelatnosti;

public class SifraDelatnostiTransformer implements ITransformer {

	@Override
	public TableDTO transformToDTO(Object entity) {
		SifraDelatnosti sd = (SifraDelatnosti) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, sd);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, sd);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<SifraDelatnosti> sd = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, sd.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, sd);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		SifraDelatnosti sd = new SifraDelatnosti();
		HashMap<String, Object> rows = entity.getFields();
		Long id;		
			
		sd.setOznakaSifreDelatnosti(rows.get("Oznaka šifre delatnosti").toString());
		sd.setNazivSifreDelatnosti(rows.get("Naziv šifre delatnosti").toString());
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			sd.setIdSifreDelatnosti(id);
		}
		
		return sd;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new SifraDelatnosti());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<SifraDelatnosti> sd) {
		for (SifraDelatnosti sifraD : sd) {
			fillData(table, sifraD);
		}		
	}
	
	private void fillData(TableDTO table, SifraDelatnosti sd) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, sd.getIdSifreDelatnosti());
						
		fields.put("Oznaka šifre delatnosti", sd.getOznakaSifreDelatnosti());
		fields.put("Naziv šifre delatnosti", sd.getNazivSifreDelatnosti());
		
		row.setFields(fields);
		row.setTableName(TableNames.SIFRA_DELATNOSTI);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.SIFRA_DELATNOSTI));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, SifraDelatnosti sd) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.PREDUZECE);
		
				
		table.setTableName(TableNames.SIFRA_DELATNOSTI);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.SIFRA_DELATNOSTI));
		table.setChildren(children);
		
		fillFields(table, sd);
	}
	
	private void fillFields(TableDTO table, SifraDelatnosti sd) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO("Oznaka šifre delatnosti", false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		field = new TableFieldDTO("Naziv šifre delatnosti", false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<SifraDelatnosti> castList(ArrayList<Object> entities) {
		ArrayList<SifraDelatnosti> menus = new ArrayList<SifraDelatnosti>();
		
		for (Object entity : entities) {
			menus.add((SifraDelatnosti)entity);
		}
		return menus;
	}

}
