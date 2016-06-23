package com.app.services;

import java.util.HashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;

public interface IGenericService {
	
	boolean create(TableRowDTO row);
	boolean update(TableRowDTO row);
	boolean delete(Long id, String tableCode);
	TableDTO getById(Long id, String tableCode);
	TableDTO getAll(String tableCode);
	HashMap<String, String> getEditableTables();
	TableDTO getMetaData(String tableCode);
	TableDTO getTableByParent(String parentTableCode, String parentId);
	TableDTO getFilteredTable(TableRowDTO filterRow);
}
