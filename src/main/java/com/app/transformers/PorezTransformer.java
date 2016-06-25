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
import com.app.model.Porez;

public class PorezTransformer implements ITransformer {

private final String VAZI = "Važi";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		Porez c = (Porez) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<Porez> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;	
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		Porez porez = new Porez();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		
		porez.setPorNaziv(rows.get(FieldNames.POREZ_LOOKUP).toString());
		porez.setPosVazi((rows.get(VAZI).toString()).equals("true"));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			porez.setIdPoreza(id);
		}
		
		return porez;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new Porez());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<Porez> c) {
		for (Porez porez : c) {
			fillData(table, porez);
		}		
	}

	private void fillData(TableDTO table, Porez c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdPoreza());				
		fields.put(FieldNames.POREZ_LOOKUP, c.getPorNaziv());
		fields.put(VAZI, c.isPosVazi());
		
		row.setFields(fields);
		row.setTableName(TableNames.POREZ);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.POREZ));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, Porez jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.PORESKA_STOPA);
		children.add(TableNames.GRUPA_PROIZVODA);
		
		ArrayList<String> parents = new ArrayList<String>();
				
		table.setTableName(TableNames.POREZ);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.POREZ));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, Porez c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POREZ_LOOKUP, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(120);
		fields.add(field);
		field = new TableFieldDTO(VAZI, false, false, false, false, "", DataTypes.BOOLEAN);
		fields.add(field);		
		table.setFields(fields);
	}
	
	private ArrayList<Porez> castList(ArrayList<Object> entities) {
		ArrayList<Porez> measures = new ArrayList<Porez>();
		
		for (Object entity : entities) {
			measures.add((Porez)entity);
		}
		return measures;
	}

}
