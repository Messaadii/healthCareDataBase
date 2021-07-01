package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.EmailAndStatusDto;
import com.healthCare.healthCareDataBase.Dtos.EmailAndVerificationCodeDto;
import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.PageableAndStringDto;
import com.healthCare.healthCareDataBase.Dtos.SearchUserGet;
import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Dtos.UpdatePasswordRequestDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateUserPasswordByEmailDto;
import com.healthCare.healthCareDataBase.Dtos.UserTypeAndUserIdDto;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.SecretaryRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@Autowired
	SecretaryRepository secretaryRepository;
	
	@GetMapping(value="/existsByUsername/{username}")
	public boolean existsByUsername(@PathVariable("username") final String username) {
		return userRepository.existsByUserUsername(username);
	}
	
	@PostMapping(value="/updateUserPasswordById")
	public boolean updateUserPasswordById(@RequestBody final UpdatePasswordRequestDto doctor) {
		doctor.setUserPassword(encoder.encode(doctor.getUserPassword()));
		userRepository.updateUserPasswordById(doctor.getUserId(),doctor.getUserPassword());
		return true;
	}
	
	@PostMapping(value="/updateUserPasswordByEmail")
	public boolean updateUserPasswordByEmail(@RequestBody final UpdateUserPasswordByEmailDto doctor) {
		doctor.setPassword(encoder.encode(doctor.getPassword()));
		userRepository.updateUserPasswordByEmail(doctor.getEmail(),doctor.getPassword());
		return true;
	}
	
	@GetMapping(value="getUserFullNameById/{id}")
	public FirstAndLastNameDto getUserFullNameById(@PathVariable("id") Long id) {
		String userType = userRepository.getUserTypeByUserId(id);
		if("doctor".equals(userType))
		  return doctorRepository.getUserFullNameById(id);
		else if ("patient".equals(userType))
		  return patientRepository.getUserFullNameById(id);
		else if ("pharmacist".equals(userType))
		  return pharmacyRepository.getUserFullNameById(id);
		else
		  return null;
	}
	
	@PostMapping(value="checkVerifacationCode")
	public boolean checkVerifacationCode(@RequestBody final EmailAndVerificationCodeDto data) {
		String userType = userRepository.getUserTypeByUsername(data.getUserEmail());
		if("doctor".equals(userType)){
			if(data.getVerificationCode().equals(doctorRepository.getVerificationCodeByEmail(data.getUserEmail())))
				return true;
			else
				return false;
		}else if ("patient".equals(userType)){
			if(data.getVerificationCode().equals(patientRepository.getVerificationCodeByEmail(data.getUserEmail())))
				return true;
			else
				return false;
				
		}else if ("secretary".equals(userType)){
			if(data.getVerificationCode().equals(secretaryRepository.getVerificationCodeByEmail(data.getUserEmail())))
				return true;
			else
				return false;
		} else {
			if(data.getVerificationCode().equals(pharmacyRepository.getVerificationCodeByEmail(data.getUserEmail())))
				return true;
			else
				return false;
		}
	}
	
	@PostMapping(value="updateUserStatusByEmail")
	public boolean updateUserStatusByEmail(@RequestBody final EmailAndStatusDto data) {
		UserTypeAndUserIdDto userType = userRepository.getUserTypeAndIdByUsername(data.getEmail());
		if("doctor".equals(userType.getUser_type()))
			  doctorRepository.changeDoctorStatusById(userType.getUser_id(),data.getStatus());
		else if ("patient".equals(userType.getUser_type()))
			  patientRepository.changePatientStatusById(userType.getUser_id(),data.getStatus());
		else if ("pharmacist".equals(userType.getUser_type()))
			  pharmacyRepository.changePharmacyStatusById(userType.getUser_id(),data.getStatus());
		else if ("secretary".equals(userType.getUser_type()))
			secretaryRepository.changePharmacyStatusById(userType.getUser_id(),data.getStatus());
		return true;
	}

	@PostMapping(value="searchUsersByName")
	public List<SearchUserGet> searchUsersByName (@RequestBody final PageableAndStringDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
		return userRepository.searchUsersByName(data.getString(),pageable);
	}
}
