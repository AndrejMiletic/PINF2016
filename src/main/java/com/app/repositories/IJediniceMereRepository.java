package com.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.JediniceMere;

@Repository
public interface IJediniceMereRepository extends CrudRepository<JediniceMere, Long>{

}
