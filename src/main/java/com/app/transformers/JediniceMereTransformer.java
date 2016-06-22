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
import com.app.model.JediniceMere;

public class JediniceMereTransformer implements ITransformer {

	private final String OZNAKA_JEDINCE = "Oznaka jedinice mere";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		JediniceMere c = (JediniceMere) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<JediniceMere> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		JediniceMere jedinica = new JediniceMere();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		
		jedinica.setNazivJediniceMere(rows.get(FieldNames.JEDINICA_MERE_LOOKUP).toString());
		jedinica.setOznakaJediniceMere(rows.get(OZNAKA_JEDINCE).toString());
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			jedinica.setIdJediniceMere(id);
		}
		
		return jedinica;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new JediniceMere());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<JediniceMere> c) {
		for (JediniceMere cenovnik : c) {
			fillData(table, cenovnik);
		}		
	}

	private void fillData(TableDTO table, JediniceMere c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdJediniceMere());				
		fields.put(FieldNames.JEDINICA_MERE_LOOKUP, c.getNazivJediniceMere());
		fields.put(OZNAKA_JEDINCE, c.getOznakaJediniceMere());
		
		row.setFields(fields);
		row.setTableName(TableNames.JEDINICE_MERE);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.JEDINICE_MERE));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, JediniceMere jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.KATALOG_ROBE_I_USLUGA);
		children.add(TableNames.STAVKE_NARUDZBE);
		
		ArrayList<String> parents = new ArrayList<String>();
				
		table.setTableName(TableNames.JEDINICE_MERE);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.JEDINICE_MERE));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, JediniceMere c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.JEDINICA_MERE_LOOKUP, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(OZNAKA_JEDINCE, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(3);
		fields.add(field);
		
		table.setFields(fields);
	}
	
	private ArrayList<JediniceMere> castList(ArrayList<Object> entities) {
		ArrayList<JediniceMere> measures = new ArrayList<JediniceMere>();
		
		for (Object entity : entities) {
			measures.add((JediniceMere)entity);
		}
		return measures;
	}
}
