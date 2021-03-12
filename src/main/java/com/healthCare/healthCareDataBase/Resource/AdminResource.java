package com.healthCare.healthCareDataBase.Resource;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Admin;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

import dtos.OneString;
import dtos.UsernameAndPassDto;

@CrossOrigin
@RestController
@RequestMapping(value="/admin")
public class AdminResource {
	
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
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Admin admin) {
		adminRepository.save(admin);
		return"admin disease with name " + admin.getAdminFullName() + " added";
	}
	
	@PostMapping(value="/getAdminSecureLoginFromUsernameAndPass")
	public String getAdminSecureLoginFromUsernameAndPass(@RequestBody final UsernameAndPassDto usernameAndPass) {
		Integer patientId = adminRepository.getAdminIdFromUsernameAndPass(usernameAndPass.getUsername(),usernameAndPass.getPassword());
		if (patientId == null)
			return "invalidInfo";
		else {
			String secureLogin=secureString(25);
			while(doctorRepository.existsByDoctorSecureLogin(secureLogin)||patientRepository.existsByPatientSecureLogin(secureLogin)||pharmacyRepository.existsByPharmacySecureLogin(secureLogin)||adminRepository.existsByAdminSecureLogin(secureLogin)) {
				secureLogin=secureString(25);
			}
			adminRepository.getAdminSecureLoginFromId(patientId, secureLogin);
			return secureLogin;
		}
	}
	
	@PostMapping(value="/getAdminInfoFromSecureLogin")
	public Admin getAdminInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return adminRepository.getAdminInfoFromSecureLogin(secureLogin.getOne());
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
