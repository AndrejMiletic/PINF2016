package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.Preduzece;
import com.app.repositories.IPreduzeceRepository;

@Component
public class PreduzeceServiceImpl implements IPreduzeceService{

	@Autowired
	private IPreduzeceRepository repo;
	
	@Override
	public Preduzece getById(Long id) {		
		return repo.findOne(id);
	}

}
