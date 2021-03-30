package com.healthCare.healthCareDataBase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Dtos.UpdatePasswordRequestDto;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value="/existsByUsername/{username}")
	public boolean existsByUsername(@PathVariable("username") final String username) {
		return userRepository.existsByUserUsername(username);
	}
	
	@PostMapping(value="/updateUserPasswordBySecurelogin")
	public boolean updateUserPasswordBySecurelogin(@RequestBody final UpdatePasswordRequestDto doctor) {
		if(userRepository.existsByUserSecureLogin(doctor.getUserSecureLogin())) {
			doctor.setUserPassword(encoder.encode(doctor.getUserPassword()));
			userRepository.updateUserPasswordBySecurelogin(doctor.getUserSecureLogin(),doctor.getUserPassword());
			return true;
		}else
			return false;
	}
	
	@PostMapping(value="/updateUsernameBySecureLogin")
	public boolean updateUsernameBySecureLogin(@RequestBody final TwoStrings twoStrings) {
		if(userRepository.existsByUserUsername(twoStrings.getStringTwo())) {
			return false;
		}else{
			userRepository.updateUsernameBySecureLogin(twoStrings.getStringOne(),twoStrings.getStringTwo());
			return true;
		}
	}
}
