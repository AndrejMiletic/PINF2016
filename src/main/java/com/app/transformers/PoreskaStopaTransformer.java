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
import com.app.model.PoreskaStopa;
import com.app.model.Porez;

public class PoreskaStopaTransformer implements ITransformer {

private final String DATUM_VAZENJA = "Datum važenja";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		PoreskaStopa c = (PoreskaStopa) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<PoreskaStopa> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;	
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		PoreskaStopa stopa = new PoreskaStopa();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		int broj;
		Date datumVazenja;
		
		broj = Integer.parseInt(rows.get(FieldNames.PORESKA_STOPA_LOOKUP).toString());
		stopa.setIznosStope(new BigDecimal(broj));
		
		datumVazenja = ConversionHelper.convertToDate(rows.get(DATUM_VAZENJA).toString());
		stopa.setDatumVazenja(datumVazenja);
		
		stopa.setPorez((Porez)fks.get(TableNames.POREZ));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			stopa.setIdStope(id);
		}
		
		return stopa;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new PoreskaStopa());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<PoreskaStopa> c) {
		for (PoreskaStopa stopa : c) {
			fillData(table, stopa);
		}		
	}

	private void fillData(TableDTO table, PoreskaStopa c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdStope());				
		fields.put(FieldNames.PORESKA_STOPA_LOOKUP, c.getIznosStope());
		fields.put(DATUM_VAZENJA, c.getDatumVazenja());
		fields.put(TableNames.POREZ, c.getPorez().getIdPoreza());
		fields.put(FieldNames.POREZ_LOOKUP, c.getPorez().getPorNaziv());
		
		row.setFields(fields);
		row.setTableName(TableNames.PORESKA_STOPA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.PORESKA_STOPA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, PoreskaStopa jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.POREZ);
		
		table.setTableName(TableNames.PORESKA_STOPA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.PORESKA_STOPA));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, PoreskaStopa c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PORESKA_STOPA_LOOKUP, false, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(DATUM_VAZENJA, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);

		field = new TableFieldDTO(TableNames.POREZ, false, false, true, false, TableNames.POREZ, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POREZ_LOOKUP, false, false, false, true, TableNames.POREZ, DataTypes.TEXT);
		fields.add(field);
		
		table.setFields(fields);
	}
	
	private ArrayList<PoreskaStopa> castList(ArrayList<Object> entities) {
		ArrayList<PoreskaStopa> measures = new ArrayList<PoreskaStopa>();
		
		for (Object entity : entities) {
			measures.add((PoreskaStopa)entity);
		}
		return measures;
	}
}
