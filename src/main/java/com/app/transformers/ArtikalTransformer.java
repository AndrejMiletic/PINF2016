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
import com.app.model.JediniceMere;
import com.app.model.KatalogRobeIUsluga;

public class ArtikalTransformer implements ITransformer {

private final String SIFRA_ARTIKLA = "Šifra artikla";
	
	@Override
	public TableDTO transformToDTO(Object entity) {
		KatalogRobeIUsluga c = (KatalogRobeIUsluga) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<KatalogRobeIUsluga> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		
		fillData(table, c);
		
		return table;	
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		KatalogRobeIUsluga artikal = new KatalogRobeIUsluga();
		HashMap<String, Object> rows = row.getFields();
		Long id;
		
		artikal.setNazivArtikla(rows.get(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP).toString());
		artikal.setSifraArtikla(rows.get(SIFRA_ARTIKLA).toString());
		
		artikal.setGrupaProizvoda((GrupaProizvoda)fks.get(TableNames.GRUPA_PROIZVODA));
		artikal.setJediniceMere((JediniceMere)fks.get(TableNames.JEDINICE_MERE));
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			artikal.setIdArtikla(id);
		}
		
		return artikal;	
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new KatalogRobeIUsluga());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<KatalogRobeIUsluga> c) {
		for (KatalogRobeIUsluga artikal : c) {
			fillData(table, artikal);
		}		
	}

	private void fillData(TableDTO table, KatalogRobeIUsluga c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdArtikla());				
		fields.put(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, c.getNazivArtikla());
		fields.put(SIFRA_ARTIKLA, c.getSifraArtikla());		

		fields.put(TableNames.GRUPA_PROIZVODA, c.getGrupaProizvoda().getIdGrupe());
		fields.put(FieldNames.GRUPA_PROIZVODA_LOOKUP, c.getGrupaProizvoda().getNazivGrupe());		

		fields.put(TableNames.JEDINICE_MERE, c.getJediniceMere().getIdJediniceMere());
		fields.put(FieldNames.JEDINICA_MERE_LOOKUP, c.getJediniceMere().getNazivJediniceMere());
		
		row.setFields(fields);
		row.setTableName(TableNames.KATALOG_ROBE_I_USLUGA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.KATALOG_ROBE_I_USLUGA));
		table.getRows().add(row);				
	}
	
	private void fillMetaData(TableDTO table, KatalogRobeIUsluga jedinica) {
		ArrayList<String> children = new ArrayList<String>();
		children.add(TableNames.STAVKE_FAKTURE_OTPREMNICE);
		children.add(TableNames.STAVKE_NARUDZBE);
		children.add(TableNames.STAVKE_CENOVNIKA);
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.JEDINICE_MERE);
		parents.add(TableNames.GRUPA_PROIZVODA);
				
		table.setTableName(TableNames.KATALOG_ROBE_I_USLUGA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.KATALOG_ROBE_I_USLUGA));
		table.setDocumentPattern(false);
		table.setDocumentChildName("");		
		table.setChildren(children);
		table.setParents(parents);
		
		fillFields(table, jedinica);
	}
	
	
	private void fillFields(TableDTO table, KatalogRobeIUsluga c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		field = new TableFieldDTO(SIFRA_ARTIKLA, false, false, false, false, "", DataTypes.TEXT);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.JEDINICE_MERE, false, false, false, false, TableNames.JEDINICE_MERE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.JEDINICA_MERE_LOOKUP, false, false, false, true, TableNames.JEDINICE_MERE, DataTypes.TEXT);
		fields.add(field);
		
		field = new TableFieldDTO(TableNames.GRUPA_PROIZVODA, false, false, false, false, TableNames.GRUPA_PROIZVODA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.GRUPA_PROIZVODA_LOOKUP, false, false, false, true, TableNames.GRUPA_PROIZVODA, DataTypes.TEXT);
		fields.add(field);
		
		table.setFields(fields);
	}
	
	private ArrayList<KatalogRobeIUsluga> castList(ArrayList<Object> entities) {
		ArrayList<KatalogRobeIUsluga> measures = new ArrayList<KatalogRobeIUsluga>();
		
		for (Object entity : entities) {
			measures.add((KatalogRobeIUsluga)entity);
		}
		return measures;
	}


}
