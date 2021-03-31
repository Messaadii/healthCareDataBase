package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.MedicalProfileDiseaseGetDto;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/medicalProfileDisease")
public class MedicalProfileDiseaseController {
	
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
	
	@PostMapping(value="getPateintMedicalProfileDiseasesByMedicalProfileId")
	public List<MedicalProfileDiseaseGetDto> getPateintMedicalProfileDiseasesByMedicalProfileId(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		Pageable pageable = PageRequest.of(pageableAndIdDto.getPage(), pageableAndIdDto.getSize(), Sort.by("medical_profile_disease_disease_id").descending());
		return medicalProfileDiseaseRepository.getPateintMedicalProfileDiseasesByMedicalProfileId(pageableAndIdDto.getId() ,pageable);
	}
	
	@GetMapping(value="getPateintMedicalProfileDiseasesNumberByMedicalProfileId/{id}")
	public Integer getPateintMedicalProfileDiseasesNumberByMedicalProfileId(@PathVariable("id") final Long id) {
		return medicalProfileDiseaseRepository.getMedicalProfileDiseasesNumber(id);
	}
}
