package com.healthCare.healthCareDataBase.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.MedicalProfile;
import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;
import com.healthCare.healthCareDataBase.Model.Patient;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/patient")
public class PatientResource {
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	MedicalProfileRepository medicalProfileRepository;
	@Autowired
	MedicalProfileDiseaseRepository medicalProfileDiseaseRepository;
	
	@GetMapping(value="/all")
	public List<Patient>getAll(){
		return patientRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Patient patient) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		patient.setPatientCreationDate(dateFormat.format(cal.getTime()));
		patientRepository.save(patient);
		return "userCreated";
	}
	@PostMapping(value="/{patientId}/medicalProfile/edit")
	public MedicalProfile editMedicalProfile(@PathVariable(name="patientId") Integer patientId,@RequestBody final MedicalProfile NewMedicalProfile) {
		medicalProfileRepository.editMedicalProfile(patientId, NewMedicalProfile.getHeight(),NewMedicalProfile.getWeight());
		return null;
	}
	@PostMapping(value="/{patientId}/medicalProfile/addDisease")
	public String addDisease(@PathVariable(name="patientId") Integer patientId ,@RequestBody final MedicalProfileDisease medicalProfileDisease) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		if(patientRepository.existsById(patientId)) {
			medicalProfileDiseaseRepository.addDiseaseToMedicalProfile(patientId,dateFormat.format(cal.getTime()),medicalProfileDisease.getMedicalProfileDiseaseDiseaseId());
			return "disease added";
		}else
		return "there is no patient with id: "+patientId;
	}
	@GetMapping(value="/getPatientIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPatientIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return patientRepository.getPatientIdFromUsernameAndPassword(username,password);
	}

}
