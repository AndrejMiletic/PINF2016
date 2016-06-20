package com.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Cenovnik;

@Repository
public interface ICenovnikRepository extends CrudRepository<Cenovnik, Long>{

}
