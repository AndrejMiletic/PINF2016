package com.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.GrupaProizvoda;

@Repository
public interface IGrupaProizvodaRepository extends CrudRepository<GrupaProizvoda, Long>{

}
