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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.DelayAppointmentRequestDto;
import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.PageableUserIdDoctorIdDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateAppointmentDateByIdDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Appointment;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Repository.AppointmentRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/appointment")
public class AppointmentController {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	@Autowired
	NotificationController notificationController;
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value="/add") 
	public boolean add(@RequestBody final Appointment appointment) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		if(appointmentRepository.checkIfAppointmentAlreadyTaken(appointment.getDoctorId(), appointment.getPatientId(),dateFormat.format(cal.getTime())) > 0)
			return false;
		else {
			List<Long> secretaries = appointmentRepository.getDoctorSecretariesById(appointment.getDoctorId());
			
			if(secretaries.size() == 0)
				appointment.setAppointmentStatus("pending");
			else
				appointment.setAppointmentStatus("unconfirmed");
			
			appointmentRepository.save(appointment);
			
			Notification not = new Notification();
			not.setIsUnread(true);
			not.setNotificationType("newAppointment");
			not.setSenderId(appointment.getPatientId());
			not.setTimeSent(dateFormat.format(cal.getTime()));
			not.setNotificationParameter(appointment.getAppointmentId()+"");
			
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("notification");
			
			for(int i =0;i<secretaries.size();i++) {
				
				not.setRecipientId(secretaries.get(i));
				
				webSocketNot.setData(userRepository.getUsernameByUserid((long)appointment.getPatientId()));

				webSocketNot.setNotification(not);
				
				template.convertAndSend("/topic/notification/"+secretaries.get(i),webSocketNot);
			}
			
			return true;
		}
	}
	
	@PostMapping(value="/appointmentsCountByDoctorIdAndDate")
	public Integer appointmentsCountByDoctorIdAndDate(@RequestBody final IntegerAndString integerAndString) {
		return appointmentRepository.appointmentsCountByDoctorIdAndDate(integerAndString.getInteger(),integerAndString.getString());
	}
	
	@DeleteMapping(value="/deleteAppointmentById/{id}")
	public boolean deleteAppointmentById(@PathVariable("id") Long id) {
		appointmentRepository.deleteById(id);
		return true;
	}
	
	@PostMapping(value="updateAppointmentDateById")
	public boolean updateAppointmentDateById(@RequestBody final UpdateAppointmentDateByIdDto data ) {
		
		List<Long> secretaries = appointmentRepository.getDoctorSecretariesById((int)data.getDoctorId());
		
		if(secretaries.size() == 0) {
			appointmentRepository.updateAppointmentDateById(data.getAppointmentId(), data.getDate(),"pending");
			return false;
		}
		else {
			Notification not = new Notification();
			
			not.setNotificationParameter(appointmentRepository.getAppointmentDateById(data.getAppointmentId()));
			appointmentRepository.updateAppointmentDateById(data.getAppointmentId(), data.getDate(),"changeDateRequest");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			
			not.setIsUnread(true);
			not.setNotificationType("changeAppDateReq");
			not.setSenderId(data.getPatientId());
			not.setTimeSent(dateFormat.format(cal.getTime()));
			
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setData(data.getAppointmentId()+"");
			webSocketNot.setExtraData(userRepository.getUsernameByUserid(data.getPatientId()));
			webSocketNot.setType("notification");

			for(int i =0;i<secretaries.size();i++) {
				
				not.setRecipientId(secretaries.get(i));
				notificationController.add(not);
				
				webSocketNot.setNotification(not);

				template.convertAndSend("/topic/notification/"+secretaries.get(i),webSocketNot);
			}
			
			return true;
		}
	}
	
	@PostMapping(value="getPatientAppointmentByPatientId")
	public List<Appointment> getPatientAppointmentByPatientId(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		Pageable pageable= PageRequest.of(pageableAndIdDto.getPage(), pageableAndIdDto.getSize(), Sort.by("appointment_date").descending());
		return appointmentRepository.getPatientAppointmentByPatientId(pageableAndIdDto.getId(), pageable);
	}
	
	@PostMapping(value="getAppointmentByDoctorIdAndDate")
	public List<Appointment> getAppointmentByDoctorIdAndDate(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		Pageable pageable= PageRequest.of(pageableAndIdDto.getPage(), pageableAndIdDto.getSize(), Sort.by("patient_turn").ascending());
		return appointmentRepository.getAppointmentByDoctorIdAndDate(pageableAndIdDto.getId(),pageableAndIdDto.getDate(), pageable);
	}
	
	@PostMapping(value="getAppointmentNumberByDoctorIdAndDate")
	public Integer getAppointmentNumberByDoctorIdAndDate(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		return appointmentRepository.getAppointmentNumberByDoctorIdAndDate(pageableAndIdDto.getId(),pageableAndIdDto.getDate());
	}
	
	@PostMapping(value="changeAppointmentStatusById")
	public boolean changeAppointmentStatusById(@RequestBody final IntegerAndString data){
		appointmentRepository.changeAppointmentStatusById(data.getInteger(),data.getString());
		return true;
	}
	
	@PostMapping(value="getAppointmentByDoctorIdAndPatientId")
	public List<Appointment> getAppointmentByDoctorIdAndPatientId(@RequestBody final PageableUserIdDoctorIdDto data) {
		Pageable pageable= PageRequest.of(data.getPage(), data.getSize(), Sort.by("appointment_date").descending());
		return appointmentRepository.getAppointmentByDoctorIdAndPatientId(data.getDoctorId(),data.getUserId(),pageable);
	}
	
	@PostMapping(value="delayAppointmentByAppId")
	public boolean delayAppointmentByAppId(@RequestBody final DelayAppointmentRequestDto data) {
		appointmentRepository.delayAppointmentByAppId(data.getAppointmentId(),data.getAllPatientNumber());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		appointmentRepository.decrementAppointmentsByDoctorIdAndDate(data.getDoctorId(),data.getPatientTurn(),dateFormat.format(cal.getTime()),data.getAppointmentId());
		
		Notification not = new Notification();
		not.setIsUnread(true);
		not.setNotificationType("delayPatientTurn");
		not.setRecipientId(data.getUserId());
		not.setSenderId(data.getDoctorId());
		not.setNotificationParameter(data.getAllPatientNumber()+"");
		not.setTimeSent(dateFormat.format(cal.getTime()));
		notificationController.add(not);
		
		WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
		webSocketNot.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
		webSocketNot.setType("notification");
		webSocketNot.setNotification(not);

		template.convertAndSend("/topic/notification/"+data.getUserId(),webSocketNot);
		return true;
	}
	
	@GetMapping(value="getAppointmentById/{id}")
	public Appointment getAppointmentById(@PathVariable final long id) {
		return appointmentRepository.getAppointmentById(id);
	}
}
