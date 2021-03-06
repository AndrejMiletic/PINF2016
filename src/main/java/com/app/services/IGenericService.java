package com.app.services;

import java.sql.Connection;
import java.util.HashMap;

import com.app.DTO.KifDTO;
import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;

public interface IGenericService {
	
	TableDTO create(TableRowDTO row);
	TableDTO update(TableRowDTO row);
	boolean delete(Long id, String tableCode);
	TableDTO getById(Long id, String tableCode);
	TableDTO getAll(String tableCode);
	HashMap<String, String> getEditableTables();
	TableDTO getMetaData(String tableCode);
	TableDTO getTableByParent(String parentTableCode, String parentId);
	TableDTO getFilteredTable(TableRowDTO filterRow);
	boolean generateKIF(Connection connection, KifDTO info);
	boolean generatePDF(Connection connection, String id);
	TableDTO getCompaniesForKIF();
	boolean generateXML(Long id,String filePath);
	double getTax(String tableCode, Long id);
	HashMap<String, Double> getCalculatedData(Long id,Long itemId,double rabat);
}
