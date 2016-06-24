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
import com.app.model.PoslovnaGodina;
import com.app.model.Preduzece;

public class PoslovnaGodinaTransformer implements ITransformer {
	
	private final String GODINA = "Godina";
	private final String ZAKLJUČENA = "Zaključena";

	@Override
	public TableDTO transformToDTO(Object entity) {

		PoslovnaGodina pg = (PoslovnaGodina) entity;
		TableDTO table = new TableDTO();		
		
		fillMetaData(table, pg);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, pg);
		
		return table;
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {

		ArrayList<PoslovnaGodina> pg = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, pg.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, pg);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		PoslovnaGodina pg = new PoslovnaGodina();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
						
		pg.setPreduzece((Preduzece)fks.get(TableNames.PREDUZECE));
		if(rows.get("Zaključena").toString().equals("true")){
			pg.setPgVazi2(true);
		}else if(rows.get("Zaključena").toString().equals("false")){
			pg.setPgVazi2(false);
		}
		
		if(rows.containsKey(GODINA)) {
			pg.setPgGodina2(Short.parseShort(rows.get(GODINA).toString()));
		}
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			pg.setIdPoslovneGodine(id);
		}
		
		return pg;
	}

	private void fillMetaData(TableDTO table, PoslovnaGodina pg) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.FAKTURA_OTPREMNICA);
		children.add(TableNames.NARUDZBA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.PREDUZECE);
				
		table.setTableName(TableNames.POSLOVNA_GODINA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.POSLOVNA_GODINA));
		table.setDocumentPattern(false);	
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, pg);
	}
	
	private void fillFields(TableDTO table, PoslovnaGodina pg) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(GODINA, false, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(ZAKLJUČENA, false, false, false, false, "", DataTypes.BOOLEAN);
		fields.add(field);
		field = new TableFieldDTO(TableNames.PREDUZECE, false, true, true, false, TableNames.PREDUZECE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP, false, false, false, true, TableNames.PREDUZECE, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}
	private void fillData(TableDTO table, PoslovnaGodina pg) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put("Id", pg.getIdPoslovneGodine());
		
		fields.put(GODINA, pg.getPgGodina2());
		fields.put(ZAKLJUČENA, pg.isPgVazi2());
		fields.put(TableNames.PREDUZECE, pg.getPreduzece().getIdPreduzeca());
		fields.put(FieldNames.PREDUZECE_LOOKUP, pg.getPreduzece().getNaziv());
		
		row.setFields(fields);
		row.setTableName(TableNames.POSLOVNA_GODINA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.POSLOVNA_GODINA));
		table.getRows().add(row);				
	}
	private ArrayList<PoslovnaGodina> castList(ArrayList<Object> entities) {
		ArrayList<PoslovnaGodina> menus = new ArrayList<PoslovnaGodina>();
		
		for (Object entity : entities) {
			menus.add((PoslovnaGodina)entity);
		}
		return menus;
	}
	private void fillData(TableDTO table, ArrayList<PoslovnaGodina> pg) {
		for (PoslovnaGodina godina : pg) {
			fillData(table, godina);
		}		
	}
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new PoslovnaGodina());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}

}
