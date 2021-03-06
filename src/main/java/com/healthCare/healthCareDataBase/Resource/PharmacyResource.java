package com.healthCare.healthCareDataBase.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/pharmacy")
public class PharmacyResource {
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	
	@GetMapping(value="/all")
	public List<Pharmacy>getAll(){
		return pharmacyRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Pharmacy pharmacy) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		pharmacy.setPharmacyCreationDate(dateFormat.format(cal.getTime()));
		if(doctorRepository.existsByDoctorUserName(pharmacy.getPharmacyUserName()) || patientRepository.existsByPatientUserName(pharmacy.getPharmacyUserName()) || pharmacyRepository.existsByPharmacyUserName(pharmacy.getPharmacyUserName()))
			return "userName already exist";
		else {
			pharmacyRepository.save(pharmacy);
			return "pharmacy with name "+ pharmacy.getPharmacyName() + " added";
		}
	}
}
