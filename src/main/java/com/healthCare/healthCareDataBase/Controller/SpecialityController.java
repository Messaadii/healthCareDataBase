package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Speciality;
import com.healthCare.healthCareDataBase.Repository.SpecialityRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/speciality")
public class SpecialityController {
	
	@Autowired
	SpecialityRepository specialityRepository;
	
	@GetMapping(value="/all")
	public List<Speciality>getAll(){
		return specialityRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Speciality speciality) {
		if(specialityRepository.existsBySpecialityName(speciality.getSpecialityName()))
			return "specialityAlreadyExist";
		else {
			specialityRepository.save(speciality);
		    return"specialityAdded";
		}	
	}
	
	@DeleteMapping(value="deleteByDoctorId/{docId}")
	@Transactional
	public boolean deleteByDoctorId(@PathVariable(name="docId") Integer docId) {
		specialityRepository.deleteByDocorId(docId);
		return true;
	}
	
	@GetMapping(value="/test/{docId}")
	public Integer test(@PathVariable("docId") Integer docId) {
		return specialityRepository.getSpecialityCodeByDoctorId(docId);
	}
}
