package com.app.transformers;

import java.util.ArrayList;

import com.app.DTO.TableDTO;

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
	 * Transformise TableDTO u niz (od bar jednog) entiteta.
	 * @param table - apstraktna reprezentacija tabele.
	 * @return niz entiteta.
	 */
	public Object transformFromDTO(TableDTO table);
}
