package com.healthCare.healthCareDataBase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoDto;
import com.healthCare.healthCareDataBase.Dtos.SecureLoginRequestDto;
import com.healthCare.healthCareDataBase.Dtos.UpdatePasswordDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateSecretaryInfoDto;
import com.healthCare.healthCareDataBase.Repository.SecretaryRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/secretary")
public class SecretaryController {

	@Autowired
	SecretaryRepository secretaryRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping(value="getSecretaryInfoBySecureLogin")
	public SecretaryInfoDto getSecretaryInfoBySecureLogin(@RequestBody final SecureLoginRequestDto data) {
		return secretaryRepository.getSecretaryInfoBySecureLogin(data.getSecureLogin());
	}
	
	@PostMapping(value="updateSecretaryInfoBySecureLogin")
	public boolean updateSecretaryInfoBySecureLogin(@RequestBody final UpdateSecretaryInfoDto data) {
		secretaryRepository.updateSecretaryInfoBySecureLogin(data.getFirstName(),data.getLastName(),data.getBirthday(),data.getGender(),data.getCity(),data.getSecureLogin());
		return true;
	}
	
	@PostMapping(value="updatePasswordBySecureLogin")
	public boolean updatePasswordBySecureLogin(@RequestBody final UpdatePasswordDto data) {
		secretaryRepository.updatePasswordBySecureLogin(encoder.encode(data.getPassword()),data.getSecureLogin());
		return true;
	}
	
}
