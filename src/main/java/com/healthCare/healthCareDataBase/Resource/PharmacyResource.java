package com.healthCare.healthCareDataBase.Resource;

import java.security.SecureRandom;
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

import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

import dtos.UsernameAndPassDto;

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
		pharmacyRepository.save(pharmacy);
		return "userCreated";
	}
	
	@GetMapping(value="/getPharmacyIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPharmacyIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return pharmacyRepository.getPharmacyIdFromUsernameAndPass(username,password);
	}
	
	@PostMapping(value="/getPharmacySecureLoginFromUsernameAndPass")
	public String getPharmacySecureLoginFromUsernameAndPass(@RequestBody final UsernameAndPassDto usernameAndPass) {
		Integer pharmacytId = pharmacyRepository.getPharmacyIdFromUsernameAndPass(usernameAndPass.getUsername(),usernameAndPass.getPassword());
		if (pharmacytId==null)
			return "invalidInfo";
		else {
			String secureLogin=secureString(25);
			while(doctorRepository.existsByDoctorSecureLogin(secureLogin)||patientRepository.existsByPatientSecureLogin(secureLogin)||pharmacyRepository.existsByPharmacySecureLogin(secureLogin)) {
				secureLogin=secureString(25);
			}
			pharmacyRepository.getPharmacySecureLoginFromId(pharmacytId, secureLogin);
			return secureLogin;
		}
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
