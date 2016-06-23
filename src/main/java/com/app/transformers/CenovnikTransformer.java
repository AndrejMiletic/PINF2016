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
import com.app.model.Cenovnik;
import com.app.model.Preduzece;

public class CenovnikTransformer implements ITransformer{

	private final String DATUM_PRIMENE = "Datum primene";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		Cenovnik c = (Cenovnik) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<Cenovnik> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		Cenovnik c = new Cenovnik();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
		
		Date datumPrimene = ConversionHelper.convertToDate(rows.get(DATUM_PRIMENE).toString());
				
		c.setPreduzece((Preduzece)fks.get(TableNames.PREDUZECE));
		c.setDatumPrimene(datumPrimene);
		
		if(rows.containsKey(FieldNames.CENOVNIK_LOOKUP)) {
			c.setNazivCenovnika(rows.get(FieldNames.CENOVNIK_LOOKUP).toString());
		}
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			c.setIdCenovnika(id);
		}
		
		return c;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new Cenovnik());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<Cenovnik> c) {
		for (Cenovnik cenovnik : c) {
			fillData(table, cenovnik);
		}		
	}
	
	private void fillData(TableDTO table, Cenovnik c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdCenovnika());
				
		if(c.getNazivCenovnika() == null || c.getNazivCenovnika().equals("")) {
			fields.put(FieldNames.CENOVNIK_LOOKUP, "");
		} else {
			fields.put(FieldNames.CENOVNIK_LOOKUP, c.getNazivCenovnika());
		}
		
		fields.put(DATUM_PRIMENE, ConversionHelper.convertDateToSrRsFormat(c.getDatumPrimene()));
		fields.put(TableNames.PREDUZECE, c.getPreduzece().getIdPreduzeca());
		fields.put(FieldNames.PREDUZECE_LOOKUP, c.getPreduzece().getNaziv());
		
		row.setFields(fields);
		row.setTableName(TableNames.CENOVNIK);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.CENOVNIK));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, Cenovnik c) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.STAVKE_CENOVNIKA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.PREDUZECE);
				
		table.setTableName(TableNames.CENOVNIK);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.CENOVNIK));
		table.setDocumentPattern(true);
		table.setDocumentChildName(TableNames.STAVKE_CENOVNIKA);		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, c);
	}
	
	private void fillFields(TableDTO table, Cenovnik c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.CENOVNIK_LOOKUP, false, true, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(DATUM_PRIMENE, false, false, false, false, "", DataTypes.DATE);
		fields.add(field);
		field = new TableFieldDTO(TableNames.PREDUZECE, false, false, true, false, TableNames.PREDUZECE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP, false, false, false, true, TableNames.PREDUZECE, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<Cenovnik> castList(ArrayList<Object> entities) {
		ArrayList<Cenovnik> menus = new ArrayList<Cenovnik>();
		
		for (Object entity : entities) {
			menus.add((Cenovnik)entity);
		}
		return menus;
	}

}
