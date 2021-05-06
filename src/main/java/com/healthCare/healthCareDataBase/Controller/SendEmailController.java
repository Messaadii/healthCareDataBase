package com.healthCare.healthCareDataBase.Controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.SendMailDto;
import com.healthCare.healthCareDataBase.Dtos.UserTypeAndUserIdDto;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;
import com.healthCare.healthCareDataBase.Service.SendEmailService;

@CrossOrigin
@RestController
@RequestMapping(value="/api/mail")
public class SendEmailController {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@PostMapping(value="/send")
	public boolean sendEmailWithBooleanReturn(@RequestBody final SendMailDto data) throws MessagingException {
		String body="Health Care Team:";
		String to="";
		if("verification".equals(data.getSubject())){
			int verificationCode = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
			UserTypeAndUserIdDto userType = userRepository.getUserTypeAndIdByUsername(data.getTo());
			if("doctor".equals(userType.getUser_type()))
				  doctorRepository.changeDoctorStatusById(userType.getUser_id(),verificationCode+"");
			else if ("patient".equals(userType.getUser_type()))
				  patientRepository.changePatientStatusById(userType.getUser_id(),verificationCode+"");
			else if ("pharmacist".equals(userType.getUser_type()))
				  pharmacyRepository.changePharmacyStatusById(userType.getUser_id(),verificationCode+"");
			to=data.getTo();
			body=verificationCode+"";
			sendEmailService.sendHtmlMessage(to, data.getSubject(),body);
			return true;
		}else {
			body = data.getBody();
			to="healthcaretnteam@gmail.com";
			sendEmailService.sendHtmlMessage(to, data.getSubject(),body);
			return true;
		}
	}
	
	public Integer sendEmail(@RequestBody final SendMailDto data) throws MessagingException {
		String body="Health Care Team:";
		String to="";
		if("verification".equals(data.getSubject())){
			int verificationCode = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
			body = body+"\r\n <b>Vefication code:</b> " + verificationCode;
			to=data.getTo();
			body=verificationCode+"";
			sendEmailService.sendHtmlMessage(to, data.getSubject(),body);
			return verificationCode;
		}else {
			body = data.getBody();
			to="healthcaretnteam@gmail.com";
			sendEmailService.sendHtmlMessage(to, data.getSubject(),body);
			return 1;
		}
	}

}
