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
import com.app.model.JediniceMere;
import com.app.model.KatalogRobeIUsluga;
import com.app.model.Narudzba;
import com.app.model.StavkeNarudzbe;

public class StavkeNarudzbeTransformer implements ITransformer {

	@Override
	public TableDTO transformToDTO(Object entity) {
		StavkeNarudzbe c = (StavkeNarudzbe) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<StavkeNarudzbe> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		StavkeNarudzbe c = new StavkeNarudzbe();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
		BigDecimal cena;
		BigDecimal iznos;
						
		c.setJediniceMere((JediniceMere)fks.get(TableNames.JEDINICE_MERE));		
		c.setKatalogRobeIUsluga((KatalogRobeIUsluga)fks.get(TableNames.KATALOG_ROBE_I_USLUGA));
		c.setNarudzba((Narudzba)fks.get(TableNames.NARUDZBA));
		
		c.setKolicinaStavkeNarudzbenice(Integer.parseInt(rows.get("Količina stavke").toString()));
		cena = new BigDecimal(rows.get("Cena bez pdv").toString());
		c.setCenaBezPdvAStavkeNarudzbenice(cena);
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			c.setIdStavkeNarudzbe(id);
			iznos = new BigDecimal(rows.get("Iznos stavke").toString());
			c.setIznosStavkeNarudzbenice(iznos);
		
		}else{
			c.setIznosStavkeNarudzbenice(new BigDecimal(0));
		}		
		return c;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new StavkeNarudzbe());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<StavkeNarudzbe> c) {
		for (StavkeNarudzbe stavka : c) {
			fillData(table, stavka);
		}		
	}
	
	private void fillData(TableDTO table, StavkeNarudzbe c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdStavkeNarudzbe());
				
		fields.put("Količina stavke", c.getKolicinaStavkeNarudzbenice());
		fields.put("Iznos stavke", c.getIznosStavkeNarudzbenice());
		fields.put("Cena bez pdv", c.getCenaBezPdvAStavkeNarudzbenice());
		
		fields.put(TableNames.JEDINICE_MERE, c.getJediniceMere().getIdJediniceMere());
		fields.put(FieldNames.JEDINICA_MERE_LOOKUP, c.getJediniceMere().getNazivJediniceMere());
		fields.put(TableNames.KATALOG_ROBE_I_USLUGA, c.getKatalogRobeIUsluga().getIdArtikla());
		fields.put(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, c.getKatalogRobeIUsluga().getNazivArtikla());
		fields.put(TableNames.NARUDZBA, c.getNarudzba().getIdNarudzbe());
		fields.put(FieldNames.NARUDZBA_LOOKUP, c.getNarudzba().getBrojNarudzbe());
		
		row.setFields(fields);
		row.setTableName(TableNames.STAVKE_NARUDZBE);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_NARUDZBE));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, StavkeNarudzbe c) {
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.JEDINICE_MERE);
		parents.add(TableNames.KATALOG_ROBE_I_USLUGA);
		parents.add(TableNames.NARUDZBA);
				
		table.setTableName(TableNames.STAVKE_NARUDZBE);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_NARUDZBE));
		table.setParents(parents);
		
		fillFields(table, c);
	}
	
	private void fillFields(TableDTO table, StavkeNarudzbe c) {		
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO("Količina stavke", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,6}");
		fields.add(field);
		field = new TableFieldDTO("Cena bez pdv", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,14}(\\.\\d{2})?");
		fields.add(field);
		field = new TableFieldDTO("Iznos stavke", false, false, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);		
		fields.add(field);
		field = new TableFieldDTO(TableNames.JEDINICE_MERE, false, false, true, false, TableNames.JEDINICE_MERE, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.JEDINICA_MERE_LOOKUP, false, false, false, true, TableNames.JEDINICE_MERE, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(TableNames.KATALOG_ROBE_I_USLUGA, false, false, true, false, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, false, false, false, true, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO(TableNames.NARUDZBA, false, false, true, false, TableNames.NARUDZBA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.NARUDZBA_LOOKUP, false, false, false, true, TableNames.NARUDZBA, DataTypes.NUMBER);
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<StavkeNarudzbe> castList(ArrayList<Object> entities) {
		ArrayList<StavkeNarudzbe> menus = new ArrayList<StavkeNarudzbe>();
		
		for (Object entity : entities) {
			menus.add((StavkeNarudzbe)entity);
		}
		return menus;
	}

}
