package com.app.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Preduzece;

@Repository
public interface IPreduzeceRepository extends CrudRepository<Preduzece, Long>{
	

	@Query("SELECT DISTINCT pp.preduzeceByIdPreduzeca "
			+ "FROM FakturaOtpremnica f inner join f.poslovniPartner pp ")
	public ArrayList<Preduzece> getCompaniesForKIF();	
}