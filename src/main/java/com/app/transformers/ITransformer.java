package com.app.transformers;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.DTO.TableDTO;
import com.app.DTO.TableRowDTO;

public interface ITransformer {
	
	/**
	 * Transformise domenski objekat u TableDTO
	 * @param entity - domenski objekat
	 * @return apstraktna reprezentacija prosledjenog objekta.
	 */
	public TableDTO transformToDTO(Object entity);
	
	/**
	 * Transformise niz domenskih objekata u TableDTO
	 * @param entities - domenski objekti
	 * @return apstraktna reprezentacija prosledjenog objekta sa svim redovima iz niza.
	 */
	public TableDTO transformToDTO(ArrayList<Object> entities);
	
	/**
	 * Vraca TableDTO sa praznim rows poljem, tj. samo sa meta podacima
	 * @return meta podaci tabele
	 */
	public TableDTO getMetaData();
	
	/**
	 * Transformise TableDTO u niz (od bar jednog) entiteta.
	 * @param table - apstraktna reprezentacija tabele.
	 * @return niz entiteta.
	 */
	public Object transformFromDTO(TableRowDTO row, HashMap<String, Object> fks);
}
