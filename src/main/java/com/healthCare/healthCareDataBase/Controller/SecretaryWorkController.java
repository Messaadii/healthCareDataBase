package com.healthCare.healthCareDataBase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.SecretaryWork;
import com.healthCare.healthCareDataBase.Repository.SecretaryWorkRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/secretaryWork")
public class SecretaryWorkController {

	@Autowired
	SecretaryWorkRepository secretaryWorkRepository;
	
	@PostMapping(value="/add") 
	public boolean add(@RequestBody final SecretaryWork data) {

		secretaryWorkRepository.save(data);
		
		return true;
	}

}
