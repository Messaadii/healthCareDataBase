package com.healthCare.healthCareDataBase.Controller;

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

import com.healthCare.healthCareDataBase.Dtos.AppointmentInfoForPatient;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PatientGetDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateMedicalProfileIdRequest;
import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;
import com.healthCare.healthCareDataBase.Model.Patient;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/patient")
public class PatientController {
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	UserRepository userRepository;
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
	
	@PostMapping(value="/{patientId}/medicalProfile/addDisease")
	public String addDisease(@PathVariable(name="patientId") Long patientId ,@RequestBody final MedicalProfileDisease medicalProfileDisease) {
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
	
	@PostMapping(value="/getPatientInfoFromSecureLogin")
	public PatientGetDto getPatientInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return patientRepository.getPatientInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updatePatientInfoBySecureLogin")
	public boolean updatePatientInfoBySecureLogin(@RequestBody final Patient patient) {
			patientRepository.updatePatientInfoBySecureLogin(patient.getUserSecureLogin(), patient.getPatientFirstName(),patient.getPatientLastName(),patient.getUserCity(),patient.getPatientBirthDay(),patient.getPatientGender());
			return true;
	}
	
	@PostMapping(value="/updateMedicalProfileByMedicalProfileId")
	public boolean updateMedicalProfileBySecureLogin(@RequestBody final UpdateMedicalProfileIdRequest medicalProfile) {
		medicalProfileRepository.updateHeightAndWeightByMedicalProfileId(medicalProfile.getId(),medicalProfile.getHeight(),medicalProfile.getWeight());
		return true;
	}
	
	@GetMapping(value="getDoctorAppointmentInfoForPatientByDoctorId/{id}")
	public AppointmentInfoForPatient getDoctorAppointmentInfoForPatientByDoctorId(@PathVariable("id") Long id) {
		return doctorRepository.getDoctorAppointmentInfoForPatientByDoctorId(id);
	}

	
}
