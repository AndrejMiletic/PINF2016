package com.app.transformers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableFieldDTO;
import com.app.DTO.TableRowDTO;
import com.app.constants.FieldNames;
import com.app.constants.TableNames;
import com.app.constants.AppConstants.DataTypes;
import com.app.helpers.ConversionHelper;
import com.app.model.Cenovnik;
import com.app.model.FakturaOtpremnica;
import com.app.model.KatalogRobeIUsluga;
import com.app.model.Preduzece;
import com.app.model.StavkeFaktureOtpremnice;

public class StavkeFaktureTransformer implements ITransformer {

	@Override
	public TableDTO transformToDTO(Object entity) {
		StavkeFaktureOtpremnice c = (StavkeFaktureOtpremnice) entity;
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c);
		
		table.setRows(new ArrayList<TableRowDTO>());		
		fillData(table, c);
		
		return table;
	}
	
	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		ArrayList<StavkeFaktureOtpremnice> c = castList(entities);
		TableDTO table = new TableDTO();
		
		fillMetaData(table, c.get(0));
			
		table.setRows(new ArrayList<TableRowDTO>());
		fillData(table, c);
		
		return table;
	}

	@Override
	public Object transformFromDTO(TableRowDTO entity, HashMap<String, Object> fks) {
		StavkeFaktureOtpremnice c = new StavkeFaktureOtpremnice();
		HashMap<String, Object> rows = entity.getFields();
		Long id;
		BigDecimal kolicina;
		BigDecimal rabat;
		BigDecimal osnovicaPdv;
		BigDecimal jedCena;
		
		c.setFakturaOtpremnica((FakturaOtpremnica)fks.get(TableNames.FAKTURA_OTPREMNICA));
		c.setKatalogRobeIUsluga((KatalogRobeIUsluga)fks.get(TableNames.KATALOG_ROBE_I_USLUGA));
		kolicina = new BigDecimal(rows.get("Količina").toString());
		c.setKolicina(kolicina);
		if(rows.containsKey("Rabat") && rows.get("Rabat")!="") {
			rabat = new BigDecimal(rows.get("Rabat").toString());
			c.setRabat(rabat);
		}		
		
		jedCena = new BigDecimal(rows.get("Jedinična cena stavke").toString());
		c.setJedinicnaCenaStavkeFakture(jedCena);
		
		
		if(rows.containsKey(FieldNames.PRIMARY_KEY)) {
			id = Long.parseLong(rows.get(FieldNames.PRIMARY_KEY).toString());
			c.setIdStavkeFakture(id);
			osnovicaPdv = new BigDecimal(rows.get("Osnovica pdv").toString());
			c.setOsnovicaPdv(osnovicaPdv);
		}else{
			c.setOsnovicaPdv(new BigDecimal(0));
		}
		
		return c;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		fillMetaData(table, new StavkeFaktureOtpremnice());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}
	
	private void fillData(TableDTO table, ArrayList<StavkeFaktureOtpremnice> c) {
		for (StavkeFaktureOtpremnice stavka : c) {
			fillData(table, stavka);
		}		
	}
	
	private void fillData(TableDTO table, StavkeFaktureOtpremnice c) {
		TableRowDTO row = new TableRowDTO();
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>(); 
		
		fields.put(FieldNames.PRIMARY_KEY, c.getIdStavkeFakture());
		
		fields.put(TableNames.FAKTURA_OTPREMNICA, c.getFakturaOtpremnica().getIdFaktureOtpremnice());
		fields.put(FieldNames.FAKTURA_OTPREMNICA_LOOKUP, c.getFakturaOtpremnica().getFaBroj());
		
		fields.put(TableNames.KATALOG_ROBE_I_USLUGA, c.getKatalogRobeIUsluga().getIdArtikla());
		fields.put(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, c.getKatalogRobeIUsluga().getNazivArtikla());
		
		fields.put("Količina", c.getKolicina());
		
		if(c.getRabat() == null || c.getRabat().equals("")) {
			fields.put("Rabat", "");
		} else {
			fields.put("Rabat", c.getRabat());
		}
		
		fields.put("Osnovica pdv", c.getOsnovicaPdv());
		if(c.getJedinicnaCenaStavkeFakture() == null || c.getJedinicnaCenaStavkeFakture().equals("")) {
			fields.put("Jedinična cena stavke", "Nije uneta cena");
		} else {
			fields.put("Jedinična cena stavke", c.getJedinicnaCenaStavkeFakture());
		}	
		
		row.setFields(fields);
		row.setTableName(TableNames.STAVKE_FAKTURE_OTPREMNICE);
		row.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_FAKTURE_OTPREMNICE));
		table.getRows().add(row);				
	}

	private void fillMetaData(TableDTO table, StavkeFaktureOtpremnice c) {
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add(TableNames.FAKTURA_OTPREMNICA);
		parents.add(TableNames.KATALOG_ROBE_I_USLUGA);
				
		table.setTableName(TableNames.STAVKE_FAKTURE_OTPREMNICE);
		table.setTableCode(ConversionHelper.getTableCode(TableNames.STAVKE_FAKTURE_OTPREMNICE));	
		table.setParents(parents);
		
		fillFields(table, c);
	}
	
	private void fillFields(TableDTO table, StavkeFaktureOtpremnice c) {
		ArrayList<TableFieldDTO> fields = new ArrayList<TableFieldDTO>();
		TableFieldDTO field;
		
		field = new TableFieldDTO(FieldNames.PRIMARY_KEY, true, false, false, false, "", DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(TableNames.FAKTURA_OTPREMNICA, false, false, true, false, TableNames.FAKTURA_OTPREMNICA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.FAKTURA_OTPREMNICA_LOOKUP, false, false, false, true, TableNames.FAKTURA_OTPREMNICA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(TableNames.KATALOG_ROBE_I_USLUGA, false, false, true, false, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.NUMBER);
		fields.add(field);
		field = new TableFieldDTO(FieldNames.KATALOG_ROBE_I_USLUGA_LOOKUP, false, false, false, true, TableNames.KATALOG_ROBE_I_USLUGA, DataTypes.TEXT);
		field.setMaxLength(100);
		fields.add(field);
		field = new TableFieldDTO("Količina", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,6}");
		fields.add(field);
		field = new TableFieldDTO("Rabat", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,14}(\\.\\d{2})?");
		fields.add(field);
		field = new TableFieldDTO("Osnovica pdv", false, false, false, false, "", DataTypes.NUMBER);
		field.setCalculated(true);
		fields.add(field);
		field = new TableFieldDTO("Jedinična cena stavke", false, false, false, false, "", DataTypes.NUMBER);
		field.setRegExp("\\d{1,14}(\\.\\d{2})?");
		fields.add(field);
		
		table.setFields(fields);
	}

	private ArrayList<StavkeFaktureOtpremnice> castList(ArrayList<Object> entities) {
		ArrayList<StavkeFaktureOtpremnice> menus = new ArrayList<StavkeFaktureOtpremnice>();
		
		for (Object entity : entities) {
			menus.add((StavkeFaktureOtpremnice)entity);
		}
		return menus;
	}



}
