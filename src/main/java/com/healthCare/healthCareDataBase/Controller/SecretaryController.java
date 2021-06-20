package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.AcceptDoctorAddRequestDto;
import com.healthCare.healthCareDataBase.Dtos.ConfirmAppointmentByIdDto;
import com.healthCare.healthCareDataBase.Dtos.GetAppointmentInfoByIdDto;
import com.healthCare.healthCareDataBase.Dtos.GetSecretaryWorkDto;
import com.healthCare.healthCareDataBase.Dtos.GetUncofirmedAppDto;
import com.healthCare.healthCareDataBase.Dtos.GetUncofirmedAppReturnDto;
import com.healthCare.healthCareDataBase.Dtos.GetUncofirmedAppWithPagReturnDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoDto;
import com.healthCare.healthCareDataBase.Dtos.SecureLoginRequestDto;
import com.healthCare.healthCareDataBase.Dtos.UpdatePasswordDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateSecretaryInfoDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Model.SecretaryWork;
import com.healthCare.healthCareDataBase.Repository.SecretaryRepository;
import com.healthCare.healthCareDataBase.Repository.SecretaryWorkRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/secretary")
public class SecretaryController {

	@Autowired
	SecretaryRepository secretaryRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	NotificationController notificationController;
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SecretaryWorkController secretaryWorkController;
	
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
	
	@GetMapping(value="getSecretaryWorkById/{id}")
	public List<GetSecretaryWorkDto> getSecretaryWorkById(@PathVariable final long id){
		return secretaryRepository.getSecretaryWorkById(id);
	}
	
	@PostMapping(value="acceptDoctorAddRequest")
	public boolean acceptDoctorAddRequest(@RequestBody final AcceptDoctorAddRequestDto data) {
		if( secretaryRepository.acceptDoctorAddRequest(data.getNotificationId(),data.getSecureLogin()) == 1) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			
			secretaryRepository.updateDoctorId(data.getSecretaryId(),data.getDoctorId());
			secretaryRepository.addEndTime(dateFormat.format(cal.getTime()),data.getSecretaryId());
			SecretaryWork secretaryWork = new SecretaryWork(data.getSecretaryId(),data.getDoctorId(),dateFormat.format(cal.getTime()),null);
			secretaryWorkController.add(secretaryWork);
			
			Notification notification = new Notification();
			notification.setNotificationType("secretaryAcceptYou");
			notification.setSenderId(data.getSecretaryId());
			notification.setRecipientId(data.getDoctorId());
			notification.setTimeSent(dateFormat.format(cal.getTime()));
			notificationController.add(notification);
			
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("notification");
			webSocketNot.setData(userRepository.getUsernameByUserid(data.getSecretaryId()));
			webSocketNot.setNotification(notification);
			template.convertAndSend("/topic/notification/"+data.getDoctorId(),webSocketNot);
			
			return true;
		}else
			return false;
	}

	@PostMapping(value="refuseDoctorAddRequest")
	public boolean refuseDoctorAddRequest(@RequestBody final AcceptDoctorAddRequestDto data) {
		if( secretaryRepository.refuseDoctorAddRequest(data.getNotificationId(),data.getSecureLogin()) == 1) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			
			Notification notification = new Notification();
			notification.setNotificationType("secretaryRefuseYou");
			notification.setSenderId(data.getSecretaryId());
			notification.setRecipientId(data.getDoctorId());
			notification.setTimeSent(dateFormat.format(cal.getTime()));
			notificationController.add(notification);
			
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("notification");
			webSocketNot.setData(userRepository.getUsernameByUserid(data.getSecretaryId()));
			webSocketNot.setNotification(notification);
			template.convertAndSend("/topic/notification/"+data.getDoctorId(),webSocketNot);
			
			return true;
		}else
			return false;
	}
	
	@PostMapping(value="getUncofirmedApp")
	public GetUncofirmedAppWithPagReturnDto getUncofirmedApp(@RequestBody final GetUncofirmedAppDto data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("appointment_date").descending());
		
		return new GetUncofirmedAppWithPagReturnDto(secretaryRepository.getUncofirmedApp(data.getSecretaryId(),data.getSecureLogin(),pageable),
				data.getPage() == 0 ? secretaryRepository.getUncofirmedAppCount(data.getSecretaryId(),data.getSecureLogin()) : 0);
	}
	
	@PostMapping(value="getAppointmentInfoById")
	public GetUncofirmedAppReturnDto getAppointmentInfoById (@RequestBody final GetAppointmentInfoByIdDto data) {
		return secretaryRepository.getAppointmentInfoById(data.getAppointmentId(),data.getSecretaryId(),data.getSecureLogin());
	}
	
	@PostMapping(value="confirmAppointmentById")
	public boolean confirmAppointmentById(@RequestBody final ConfirmAppointmentByIdDto data) {
		secretaryRepository.updateAppointmentStatusById(data.getAppointmentId(),data.getSecretaryId(),data.getSecureLogin(),"pending");
		secretaryRepository.updateAppointmentTurnById(data.getAppointmentId(),secretaryRepository.patientNewTurn(data.getAppointmentId()));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		Notification notification = new Notification();
		if("unconfirmed".equals(data.getAppointmentStatus()))
			notification.setNotificationType("secretaryConfirmAppointment");
		else
			notification.setNotificationType("secretaryConfirmAppDateChange");
		notification.setSenderId(data.getDoctorId());
		notification.setRecipientId(data.getPatientId());
		notification.setNotificationParameter(data.getAppointmentId()+"");
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notificationController.add(notification);
		
		WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
		webSocketNot.setType("notification");
		webSocketNot.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
		webSocketNot.setNotification(notification);
		template.convertAndSend("/topic/notification/"+data.getPatientId(),webSocketNot);
		
		return true;
	}
	
	@PostMapping(value="refuseAppointmentById")
	public boolean refuseAppointmentById(@RequestBody final ConfirmAppointmentByIdDto data) {
		Notification notification = new Notification();
		if("unconfirmed".equals(data.getAppointmentStatus()))
			notification.setNotificationType("secretaryRefuseAppointment");
		else {
			notification.setNotificationType("secretaryRefuseAppDateChange");
			secretaryRepository.cancelAppointmentChangeDateRequest(data.getAppointmentId(),data.getSecretaryId(),data.getPatientId());
		}
		
		
		secretaryRepository.updateAppointmentStatusById(data.getAppointmentId(),data.getSecretaryId(),data.getSecureLogin(),
				"unconfirmed".equals(data.getAppointmentStatus()) ? "refused" : "pending");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		notification.setSenderId(data.getDoctorId());
		notification.setRecipientId(data.getPatientId());
		notification.setNotificationParameter(data.getAppointmentId()+"");
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notificationController.add(notification);
		
		WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
		webSocketNot.setType("notification");
		webSocketNot.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
		webSocketNot.setNotification(notification);
		template.convertAndSend("/topic/notification/"+data.getPatientId(),webSocketNot);
		
		return true;
	}
}
