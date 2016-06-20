package com.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.FakturaOtpremnica;

@Repository
public interface IFakturaOtpremnicaRepository extends CrudRepository<FakturaOtpremnica, Long>{

}

