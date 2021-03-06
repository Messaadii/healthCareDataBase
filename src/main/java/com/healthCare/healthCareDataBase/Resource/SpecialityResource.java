package com.healthCare.healthCareDataBase.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Speciality;
import com.healthCare.healthCareDataBase.Repository.SpecialityRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/speciality")
public class SpecialityResource {
	
	@Autowired
	SpecialityRepository specialityRepository;
	
	@GetMapping(value="/all")
	public List<Speciality>getAll(){
		return specialityRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Speciality speciality) {
		if(specialityRepository.existsBySpecialityName(speciality.getSpecialityName()))
			return "speciality already exist";
		else {
			specialityRepository.save(speciality);
		    return"speciality with name " + speciality.getSpecialityName() + " added";
		}	
	}
}
