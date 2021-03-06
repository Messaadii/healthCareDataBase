package com.healthCare.healthCareDataBase.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/medicalProfileDisease")
public class MedicalProfileDiseaseResource {
	
	@Autowired
	MedicalProfileDiseaseRepository medicalProfileDiseaseRepository;
	
	@GetMapping(value="/all")
	public List<MedicalProfileDisease>getAll(){
		return medicalProfileDiseaseRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final MedicalProfileDisease medicalProfileDisease) {
		medicalProfileDiseaseRepository.save(medicalProfileDisease);
		return"medical profile disease with id " + medicalProfileDisease.getMedicalProfileDiseaseId() + " added";
	}
}
