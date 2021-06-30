package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.AdminGetDto;
import com.healthCare.healthCareDataBase.Dtos.GetUserIdDto;
import com.healthCare.healthCareDataBase.Model.Admin;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@GetMapping(value="/all")
	public List<Admin> getAll(){
		return adminRepository.findAll();
	}
	
	@PostMapping(value="/getAdminInfoById")
	public AdminGetDto getAdminInfoById(@RequestBody GetUserIdDto data) {
		return adminRepository.getAdminInfoById(data.getUserId());
	}
}
