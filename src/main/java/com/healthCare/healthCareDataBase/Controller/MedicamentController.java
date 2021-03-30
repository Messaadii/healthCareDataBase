package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Medicament;
import com.healthCare.healthCareDataBase.Repository.MedicamentRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/medicament")
public class MedicamentController {
	
	@Autowired
	MedicamentRepository medicamentRepository;
	
	@GetMapping(value="/all")
	public List<Medicament>getAll(){
		return medicamentRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Medicament medicament) {
		medicamentRepository.save(medicament);
		return"medicament with name " + medicament.getMedicamentName() + " added";
	}
}
