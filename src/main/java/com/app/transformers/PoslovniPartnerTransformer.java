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
import com.app.model.PoslovniPartner;
import com.app.model.Preduzece;

public class PoslovniPartnerTransformer implements ITransformer {

	
	@Override
	public TableDTO transformToDTO(Object entity) {
		PoslovniPartner pp = (PoslovniPartner) entity;
		TableDTO table = new TableDTO();		
		
		fillMetaData(table, pp);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, pp);
		
		return table;
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<PoslovniPartner> pp = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, pp.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, pp);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		PoslovniPartner pp = new PoslovniPartner();
		HashMap<String, Object> rows = row.getFields();
		Long id;
				
		pp.setPreduzeceByIdPartnera((Preduzece)fks.get(TableNames.PARTNER));
		pp.setPreduzeceByIdPreduzeca((Preduzece)fks.get(TableNames.PREDUZECE));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			pp.setIdPartnerstva(id);
		}
		
		if(rows.containsKey("Vrsta")) {
			pp.setVrsta(rows.get("Vrsta").toString().charAt(0));
		}
		
		return pp;
	}

	private void fillMetaData(TableDTO table, PoslovniPartner pp) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.NARUDZBA);
		children.add(TableNames.FAKTURA_OTPREMNICA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.PREDUZECE);
		parents.add(TableNames.PREDUZECE);
				
		table.setTableName(TableNames.POSLOVNI_PARTNER);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.POSLOVNI_PARTNER));
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, pp);
	}
	private void fillFields(TableDTO table, PoslovniPartner pp) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(TableNames.PREDUZECE, false, false, true, false, TableNames.PREDUZECE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(TableNames.PARTNER, false, false, true, false, TableNames.PREDUZECE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO("Vrsta", false, false, false, false, "", DataTypes.CHAR);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP_PREDUZECE, false, false, false, true, TableNames.PREDUZECE, DataTypes.TEXT);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP_PARTNER, false, false, false, true, TableNames.PREDUZECE, DataTypes.TEXT);
		fields.add(field);
		
		table.setFields(fields);
	}
	private void fillData(TableDTO table, PoslovniPartner pp) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put("Id", pp.getIdPartnerstva());
		
		fields.put("Vrsta", pp.getVrsta());
		fields.put(TableNames.PARTNER, pp.getPreduzeceByIdPartnera().getIdPreduzeca());
		fields.put(TableNames.PREDUZECE, pp.getPreduzeceByIdPreduzeca().getIdPreduzeca());
		fields.put(FieldNames.PREDUZECE_LOOKUP_PREDUZECE, pp.getPreduzeceByIdPreduzeca().getNaziv());
		fields.put(FieldNames.PREDUZECE_LOOKUP_PARTNER, pp.getPreduzeceByIdPartnera().getNaziv());
		
		row.setFields(fields);
		row.setTableName(TableNames.POSLOVNI_PARTNER);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.POSLOVNI_PARTNER));
		table.getRows().add(row);				
	}
	private ArrayList<PoslovniPartner> castList(ArrayList<Object> entities) {
		ArrayList<PoslovniPartner> menus = new ArrayList<PoslovniPartner>();
		
		for (Object entity : entities) {
			menus.add((PoslovniPartner)entity);
		}
		return menus;
	}
	private void fillData(TableDTO table, ArrayList<PoslovniPartner> pp) {
		for (PoslovniPartner poslovniPartner : pp) {
			fillData(table, poslovniPartner);
		}		
	}
}
