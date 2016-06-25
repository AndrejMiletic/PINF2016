package com.app.transformers;

import java.math.BigDecimal;
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
import com.app.model.KatalogRobeIUsluga;
import com.app.model.StavkeCenovnika;

public class StavkeCenovnikaTransformer implements ITransformer {

	@Override
	public TableDTO transformToDTO(Object entity) {
		StavkeCenovnika sc = (StavkeCenovnika) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, sc);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, sc);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<StavkeCenovnika> sc = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, sc.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, sc);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		StavkeCenovnika sc = new StavkeCenovnika();
		HashMap<String, Object> rows = entity.getFields();
		Long id;	
		BigDecimal cena;
					
		sc.setCenovnik((Cenovnik)fks.get(TableNames.CENOVNIK));
		sc.setKatalogRobeIUsluga((KatalogRobeIUsluga)fks.get(TableNames.KATALOG_ROBE_I_USLUGA));
	    cena = new BigDecimal(rows.get("Jedinična cena stavke").toString());
		sc.setJedinicnaCenaStavkeCenovnika(cena);
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			sc.setIdStavkeCenovnika(id);
		}
		
		return sc;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new StavkeCenovnika());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<StavkeCenovnika> sc) {
		for (StavkeCenovnika stavka : sc) {
			fillData(table, stavka);
		}		
	}
	
	private void fillData(TableDTO table, StavkeCenovnika sc) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, sc.getIdStavkeCenovnika());
						
		fields.put("Jedinična cena stavke", sc.getJedinicnaCenaStavkeCenovnika());
		fields.put(TableNames.CENOVNIK, sc.getCenovnik().getIdCenovnika());
		fields.put(FieldNames.CENOVNIK_LOOKUP, sc.getCenovnik().getNazivCenovnika());
		fields.put(TableNames.KATALOG_ROBE_I_USLUGA, sc.getKatalogRobeIUsluga().getIdArtikla());
		fields.put(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, sc.getKatalogRobeIUsluga().getNazivArtikla());
		
		row.setFields(fields);
		row.setTableName(TableNames.STAVKE_CENOVNIKA);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_CENOVNIKA));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, StavkeCenovnika sc) {		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.CENOVNIK);
		parents.add(TableNames.KATALOG_ROBE_I_USLUGA);
				
		table.setTableName(TableNames.STAVKE_CENOVNIKA);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_CENOVNIKA));
		table.setParents(parents);
		
		fillFields(table, sc);
	}
	
	private void fillFields(TableDTO table, StavkeCenovnika sc) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO("Jedinična cena stavke", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,14}(\\.\\d{2})?");
		fields.add(field);
		field = new TableFieldDTO(TableNames.CENOVNIK, false, false, true, false, TableNames.CENOVNIK, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.CENOVNIK_LOOKUP, false, false, false, true, TableNames.CENOVNIK, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(TableNames.KATALOG_ROBE_I_USLUGA, false, false, true, false, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, false, false, false, true, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<StavkeCenovnika> castList(ArrayList<Object> entities) {
		ArrayList<StavkeCenovnika> menus = new ArrayList<StavkeCenovnika>();
		
		for (Object entity : entities) {
			menus.add((StavkeCenovnika)entity);
		}
		return menus;
	}

}
