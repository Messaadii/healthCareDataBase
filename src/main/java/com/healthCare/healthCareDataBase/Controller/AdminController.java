package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.AdminGetDto;
import com.healthCare.healthCareDataBase.Dtos.AdminsDto;
import com.healthCare.healthCareDataBase.Dtos.ChangeAdminPositionDto;
import com.healthCare.healthCareDataBase.Dtos.GetAdminsDto;
import com.healthCare.healthCareDataBase.Dtos.GetUserIdDto;
import com.healthCare.healthCareDataBase.Dtos.SendMailDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Admin;
import com.healthCare.healthCareDataBase.Model.ERole;
import com.healthCare.healthCareDataBase.Model.Role;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.RoleRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	SendEmailController sendEmailController;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
    private SimpMessagingTemplate template;
	
	@GetMapping(value="/all")
	public List<Admin> getAll(){
		return adminRepository.findAll();
	}
	
	@PostMapping(value="/getAdminInfoById")
	public AdminGetDto getAdminInfoById(@RequestBody GetUserIdDto data) {
		return adminRepository.getAdminInfoById(data.getUserId());
	}
	
	@PostMapping(value="/getAdmins")
	public List<AdminsDto> getAdmins(@RequestBody GetAdminsDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("user_id").descending());
		return adminRepository.getAdmins(pageable);
	}
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody Admin data) throws MessagingException {
		if(userRepository.existsByUserUsername(data.getUserUsername())) 
			return false;
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			data.setUserCreationDate(dateFormat.format(cal.getTime()));
			data.setUserPassword(encoder.encode(data.getUserPassword()));
			SendMailDto mail=new SendMailDto("verification","",data.getUserUsername());
			data.setAdminPosition(sendEmailController.sendEmail(mail)+"");
			
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ADMIN_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			data.setRoles(roles);
			adminRepository.save(data);
			return true;
		}
	}
	
	@PostMapping(value="/changeAdminPosition")
	public boolean changeAdminPosition(@RequestBody ChangeAdminPositionDto data) {
		adminRepository.changeAdminPosition(data.getUserId(),data.getPosition());
		
		WebSocketNotificationDto webSocket = new WebSocketNotificationDto();
		webSocket.setData(userRepository.getUsernameByUserid(data.getAdminId()));
		webSocket.setType("changePositionTo"+data.getPosition());
		template.convertAndSend("/topic/notification/"+data.getUserId(),webSocket);
		
		return true;
	}
	
	@Modifying
	@DeleteMapping(value="/deleteAdmin/{id}")
	public boolean deleteAdmin(@PathVariable long id) {
		adminRepository.deleteById(id);
		return true;
	}
}
