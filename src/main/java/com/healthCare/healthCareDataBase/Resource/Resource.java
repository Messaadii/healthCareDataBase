package com.healthCare.healthCareDataBase.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="resource")
public class Resource {
	
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping(value="/ExistsByUsername/{username}")
	public boolean existsByUserName(@PathVariable(name="username") String username) {
		if(doctorRepository.existsByDoctorUserName(username)|| patientRepository.existsByPatientUserName(username)||pharmacyRepository.existsByPharmacyUserName(username)||adminRepository.existsByAdminUserName(username))
			return true;
		else
			return false;
	}
}
