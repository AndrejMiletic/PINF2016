package com.app.transformers;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;

public class FakturaTransformer implements ITransformer{

	@Override
	public TableDTO transformToDTO(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableDTO transformToDTO(ArrayList<Object> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TableDTO getMetaData() {
		TableDTO table = new TableDTO();
		
		//fillMetaData(table, new KatalogRobeIUsluga());
		
		table.setRows(new ArrayList<TableRowDTO>());
		
		return table;
	}

}
