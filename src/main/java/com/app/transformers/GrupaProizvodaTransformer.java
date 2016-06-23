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
import com.app.model.GrupaProizvoda;
import com.app.model.Porez;
import com.app.model.Preduzece;

public class GrupaProizvodaTransformer implements ITransformer {

	private final String SIFRA_GRUPE = "Šifra grupe";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		GrupaProizvoda c = (GrupaProizvoda) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<GrupaProizvoda> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		GrupaProizvoda gp = new GrupaProizvoda();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
		
		gp.setNazivGrupe(rows.get(FieldNames.GRUPA_PROIZVODA_LOOKUP).toString());
		gp.setSifraGrupe(rows.get(SIFRA_GRUPE).toString());
		
		gp.setPorez((Porez) fks.get(TableNames.POREZ));
		gp.setPreduzece((Preduzece) fks.get(TableNames.PREDUZECE));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			gp.setIdGrupe(id);
		}
		
		return gp;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new GrupaProizvoda());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<GrupaProizvoda> c) {
		for (GrupaProizvoda grupa : c) {
			fillData(table, grupa);
		}		
	}
	
	private void fillData(TableDTO table, GrupaProizvoda gr) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, gr.getIdGrupe());		
		
		fields.put(FieldNames.GRUPA_PROIZVODA_LOOKUP, gr.getNazivGrupe());
		fields.put(SIFRA_GRUPE, gr.getSifraGrupe());
		
		fields.put(TableNames.PREDUZECE, gr.getPreduzece().getIdPreduzeca());
		fields.put(FieldNames.PREDUZECE_LOOKUP, gr.getPreduzece().getNaziv());
		
		fields.put(TableNames.POREZ, gr.getPorez().getIdPoreza());
		fields.put(FieldNames.POREZ_LOOKUP, gr.getPorez().getPorNaziv());
		
		row.setFields(fields);
		row.setTableName(TableNames.GRUPA_PROIZVODA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.GRUPA_PROIZVODA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, GrupaProizvoda c) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.KATALOG_ROBE_I_USLUGA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.PREDUZECE);
		parents.add(TableNames.POREZ);
				
		table.setTableName(TableNames.GRUPA_PROIZVODA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.GRUPA_PROIZVODA));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, c);
	}
	
	private void fillFields(TableDTO table, GrupaProizvoda c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.GRUPA_PROIZVODA_LOOKUP, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(SIFRA_GRUPE, false, false, false, false, "", DataTypes.TEXT);
		field.setMaxLength(4);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.PREDUZECE, false, false, true, false, TableNames.PREDUZECE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.PREDUZECE_LOOKUP, false, false, false, true, TableNames.PREDUZECE, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.POREZ, false, false, true, false, TableNames.POREZ, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.POREZ_LOOKUP, false, false, false, true, TableNames.POREZ, DataTypes.TEXT);
		field.setMaxLength(120);
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<GrupaProizvoda> castList(ArrayList<Object> entities) {
		ArrayList<GrupaProizvoda> groups = new ArrayList<GrupaProizvoda>();
		
		for (Object entity : entities) {
			groups.add((GrupaProizvoda)entity);
		}
		return groups;
	}
	
}
