package com.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Preduzece;

@Repository
public interface IPreduzeceRepository extends CrudRepository<Preduzece, Long>{

}