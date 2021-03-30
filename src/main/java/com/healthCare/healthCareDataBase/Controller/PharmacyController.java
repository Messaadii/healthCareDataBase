package com.healthCare.healthCareDataBase.Controller;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/pharmacy")
public class PharmacyController {
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping(value="/all")
	public List<Pharmacy>getAll(){
		return pharmacyRepository.findAll();
	}
	
	@GetMapping(value="/getPharmacyIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPharmacyIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return pharmacyRepository.getPharmacyIdFromUsernameAndPass(username,password);
	}
	
	public String secureString(int len){
		 String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz&é(-/+*)=}@à^ç_è[]{#";
		 SecureRandom rnd = new SecureRandom();
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
		 }
}
