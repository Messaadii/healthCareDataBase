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

import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;
import com.healthCare.healthCareDataBase.Model.Patient;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

import dtos.OneString;
import dtos.StringAndTowDouble;
import dtos.UsernameAndPassDto;

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
	@Autowired
	AdminRepository adminRepository;
	
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
		return patientRepository.getPatientIdFromUsernameAndPass(username,password);
	}
	
	@PostMapping(value="/getPatientSecureLoginFromUsernameAndPass")
	public String getPatientSecureLoginFromUsernameAndPass(@RequestBody final UsernameAndPassDto usernameAndPass) {
		Integer patientId = patientRepository.getPatientIdFromUsernameAndPass(usernameAndPass.getUsername(),usernameAndPass.getPassword());
		if (patientId == null)
			return "invalidInfo";
		else {
			String secureLogin=secureString(25);
			while(doctorRepository.existsByDoctorSecureLogin(secureLogin)||patientRepository.existsByPatientSecureLogin(secureLogin)||pharmacyRepository.existsByPharmacySecureLogin(secureLogin)||adminRepository.existsByAdminSecureLogin(secureLogin)) {
				secureLogin=secureString(25);
			}
			patientRepository.getPatientSecureLoginFromId(patientId, secureLogin);
			return secureLogin;
		}
	}
	
	@PostMapping(value="/getPatientInfoFromSecureLogin")
	public Patient getPatientInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return patientRepository.getPatientInfoFromSecureLogin(secureLogin.getOne());
	}
	
	public String secureString(int len){
		 String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz&é(-/+*)=}@à^ç_è[]{#";
		 SecureRandom rnd = new SecureRandom();
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
		   }

	@PostMapping(value="/updatePatientInfoBySecureLogin")
	public String updatePatientInfoBySecureLogin(@RequestBody final Patient patient) {
		if(patientRepository.existsByPatientUserName(patient.getPatientUserName()) || pharmacyRepository.existsByPharmacyUserName(patient.getPatientUserName()) || doctorRepository.existsByDoctorUserName(patient.getPatientUserName())|| adminRepository.existsByAdminUserName(patient.getPatientUserName())) {
			if(patientRepository.findUserNameBySecureLogin(patient.getPatientSecureLogin()).equals(patient.getPatientUserName())){ 
				  patientRepository.updatePatientInfoBySecureLogin(patient.getPatientSecureLogin(),patient.getPatientUserName(), patient.getPatientFirstName(),patient.getPatientLastName(),patient.getPatientCity(),patient.getPatientBirthDay(),patient.getPatientGender(),patient.getPatientPassword());
				  return "updated";
				}else
				  return "usernameExist";
		}
		else {
			patientRepository.updatePatientInfoBySecureLogin(patient.getPatientSecureLogin(),patient.getPatientUserName(), patient.getPatientFirstName(),patient.getPatientLastName(),patient.getPatientCity(),patient.getPatientBirthDay(),patient.getPatientGender(),patient.getPatientPassword());
			return "updated";
		}
	}
	
	@PostMapping(value="/updateMedicalProfileBySecureLogin")
	public String updateMedicalProfileBySecureLogin(@RequestBody final StringAndTowDouble stringAndTowDouble) {
		medicalProfileRepository.updateHeightAndWeightByMedicalProfileId(patientRepository.getPatientIdFromSecureLogin(stringAndTowDouble.getString()),stringAndTowDouble.getDouble1(),stringAndTowDouble.getDouble2());
		return "updated";
	}
}
