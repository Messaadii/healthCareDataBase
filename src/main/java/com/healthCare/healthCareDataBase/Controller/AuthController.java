package com.healthCare.healthCareDataBase.Controller;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Admin;
import com.healthCare.healthCareDataBase.Model.Doctor;
import com.healthCare.healthCareDataBase.Model.ERole;
import com.healthCare.healthCareDataBase.Model.Patient;
import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Model.Role;
import com.healthCare.healthCareDataBase.Repository.RoleRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;
import com.healthCare.healthCareDataBase.Dtos.JwtResponseDto;
import com.healthCare.healthCareDataBase.Dtos.LoginRequestDto;
import com.healthCare.healthCareDataBase.Dtos.MessageResponseDto;
import com.healthCare.healthCareDataBase.Dtos.SignupRequestDto;
import com.healthCare.healthCareDataBase.Security.jwt.JwtUtils;
import com.healthCare.healthCareDataBase.Security.service.UserDetailsImpl;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getUserPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		String secureLogin = genrateSecureLogin(25);
		while(userRepository.existsByUserSecureLogin(secureLogin)) {
			secureLogin = genrateSecureLogin(25);
		}
		
		userRepository.updateUserSecureLoginByUserId(userDetails.getId(),secureLogin);
		
		return ResponseEntity.ok(new JwtResponseDto(jwt, 
				 secureLogin,
				 roles));
	}
	
	public String genrateSecureLogin(int len){
		 String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz&é(-/+*)=}@à^ç_è[]{#";
		 SecureRandom rnd = new SecureRandom();
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
		   }

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
		if (userRepository.existsByUserUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponseDto("Error: Username is already taken!"));
		}

		Set<Role> roles = new HashSet<>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		if("PATIENT_ROLE".equals(signUpRequest.getUserRole())){
			Role userRole = roleRepository.findByName(ERole.PATIENT_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			Patient patient= new Patient(signUpRequest.getUsername(), encoder.encode(signUpRequest.getUserPassword()), signUpRequest.getUserCity(), roles, dateFormat.format(cal.getTime()), signUpRequest.getUserFirstName(), signUpRequest.getUserLastName(), signUpRequest.getUserBirthday(), signUpRequest.getUserGender());
			patientRepository.save(patient);
			
		}else if("DOCTOR_ROLE".equals(signUpRequest.getUserRole())){
			Role userRole = roleRepository.findByName(ERole.DOCTOR_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			Doctor doctor= new Doctor(signUpRequest.getUsername(), encoder.encode(signUpRequest.getUserPassword()), signUpRequest.getUserCity(), roles, dateFormat.format(cal.getTime()), signUpRequest.getUserFirstName(), signUpRequest.getUserLastName(), signUpRequest.getUserBirthday(), signUpRequest.getUserGender(), "notApproved");
			doctorRepository.save(doctor);
		}else if("ADMIN_ROLE".equals(signUpRequest.getUserRole())){
			Role userRole = roleRepository.findByName(ERole.ADMIN_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			Admin admin= new Admin(signUpRequest.getUsername(), encoder.encode(signUpRequest.getUserPassword()), signUpRequest.getUserCity(), roles, dateFormat.format(cal.getTime()), signUpRequest.getUserFullName());
			adminRepository.save(admin);
		}else if("PHARMACIST_ROLE".equals(signUpRequest.getUserRole())){
			Role userRole = roleRepository.findByName(ERole.PHARMACIST_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			Pharmacy pharmacy= new Pharmacy(signUpRequest.getUsername(), encoder.encode(signUpRequest.getUserPassword()), signUpRequest.getUserCity(), roles, dateFormat.format(cal.getTime()), signUpRequest.getUserFullName(),"notApproved");
			pharmacyRepository.save(pharmacy);
		}else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponseDto("Error: Invalid role!"));
		}
		
		return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
	}
}