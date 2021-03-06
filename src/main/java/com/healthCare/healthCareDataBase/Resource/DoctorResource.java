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

import com.healthCare.healthCareDataBase.Model.Doctor;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.SpecialityRepository;

import dtos.UsernameAndPassDto;

@CrossOrigin
@RestController
@RequestMapping(value="/doctor")
public class DoctorResource {
	
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	SpecialityRepository specialityRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping(value="/all")
	public List<Doctor>getAll(){
		return doctorRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Doctor doctor) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		doctor.setDoctorCreationDate(dateFormat.format(cal.getTime()));
		doctor.setDoctorStatus("notApproved");
		doctorRepository.save(doctor);
		return"userCreated";
	}
	@PostMapping(value="/{doctorId}/addspeciality")
	public String addSpeciality(@PathVariable(name="doctorId") Integer doctorId,@RequestBody final Integer specialityId) {
		if(specialityRepository.existsBySpecialityId(specialityId)) {
			if(specialityRepository.checkIfDoctorAlreadyHaveTheSpeciality(doctorId,specialityId)==doctorId)
				return "doctor already have this speciality";
			else {
				specialityRepository.addSpecialityToDoctor(doctorId,specialityId);
				return "speciality with id " +specialityId+" added to doctor: " +doctorId;
			}
		}else
		    return "there is no speciality with id: " + specialityId;
	}
	@PostMapping(value="/getDoctorSecureLoginFromUsernameAndPass")
	public String getDoctorSecureLoginFromUsernameAndPass(@RequestBody final UsernameAndPassDto usernameAndPass) {
		Integer docId = doctorRepository.getDoctorIdFromUsernameAndPass(usernameAndPass.getUsername(),usernameAndPass.getPassword());
		if(docId==null) {
			return "invalidInfo";
		}else {
			String secureLogin=secureString(25);
			while(doctorRepository.existsByDoctorSecureLogin(secureLogin)||patientRepository.existsByPatientSecureLogin(secureLogin)||pharmacyRepository.existsByPharmacySecureLogin(secureLogin)||adminRepository.existsByAdminSecureLogin(secureLogin)) {
				secureLogin=secureString(25);
			}
			doctorRepository.getDoctorSecureLoginFromId(docId, secureLogin);
			return secureLogin;
		}	
	}
	public String secureString(int len){
		 String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz&??(-/+*)=}@??^??_??[]{#";
		 SecureRandom rnd = new SecureRandom();
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
		   }

	@GetMapping(value="/getNotApprovedDoctors")
	public List<Doctor> getNotApprovedDoctors() {
		return doctorRepository.getNotApprovedDoctors();
	}

}

