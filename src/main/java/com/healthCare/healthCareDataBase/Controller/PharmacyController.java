package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value="/all")
	public List<Pharmacy>getAll(){
		return pharmacyRepository.findAll();
	}
	
	@GetMapping(value="/getPharmacyIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPharmacyIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return pharmacyRepository.getPharmacyIdFromUsernameAndPass(username,password);
	}
	
	@PostMapping(value="/getPharmacyInfoFromSecureLogin")
	public PharmacyGetDto getPharmacyInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return pharmacyRepository.getPharmacyInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updatePharmacyInfoBySecureLogin")
	public boolean updatePharmacyInfoBySecureLogin(@RequestBody final Pharmacy pharmacy) {
		pharmacyRepository.updatePharmacyInfoBySecureLogin(pharmacy.getUserSecureLogin(), pharmacy.getPharmacyFullName(),pharmacy.getUserCity());
			return true;
	}
	
	@PostMapping(value="changePharamcyStatusBySecureLogin")
	public boolean changePharamcyStatusBySecureLogin(@RequestBody final TwoStrings data) {
		pharmacyRepository.changePharamcyStatusBySecureLogin(data.getStringOne(),data.getStringTwo());
		return true;
	}
}
