package com.healthCare.healthCareDataBase.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.MedicalProfile;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/MedicalProfile")
public class MedicalProfileResource {
	
	@Autowired
	MedicalProfileRepository medicalProfileRepository;
	
	@GetMapping(value="/all")
	public List<MedicalProfile>getAll(){
		return medicalProfileRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final MedicalProfile medicalProfile) {
		    medicalProfileRepository.save(medicalProfile);
			return"medical profile with id " + medicalProfile.getMedicalProfileId() + " added";
	}

}
