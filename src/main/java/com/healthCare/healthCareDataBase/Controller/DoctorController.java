package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

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

import com.healthCare.healthCareDataBase.Dtos.AppUsersInfoDto;
import com.healthCare.healthCareDataBase.Dtos.AppointmentDocInfo;
import com.healthCare.healthCareDataBase.Dtos.AppointmentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.CurrentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.DoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.DoctorSettingsDto;
import com.healthCare.healthCareDataBase.Dtos.IdTurnAndDate;
import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PageableDto;
import com.healthCare.healthCareDataBase.Dtos.PendingDoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDocDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDoctorDto;
import com.healthCare.healthCareDataBase.Dtos.SecureLoginAndPatientTurnDto;
import com.healthCare.healthCareDataBase.Dtos.TopRatedDoctorsDto;
import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Dtos.UpdatePositionDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Doctor;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.AppointmentRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.SpecialityRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/doctor")
public class DoctorController {
	
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	SpecialityRepository specialityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationController notificationController;
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	AppointmentRepository appointmentRepository;
	
	
	@GetMapping(value="/all")
	public List<Doctor>getAll(){
		return doctorRepository.findAll();
	}
	
	@PostMapping(value="/addspeciality")
	public boolean addSpeciality(@RequestBody final IntegerAndString integerAndString) {
		Integer specId = specialityRepository.getSpecialityIdBySpecialityCode(integerAndString.getString());
		if(specialityRepository.checkIfDoctorAlreadyHaveTheSpeciality(integerAndString.getInteger(),specId)==integerAndString.getInteger())
			return false;
		else {
			specialityRepository.addSpecialityToDoctor(integerAndString.getInteger(),specId);
			return true;
		}
	}
	
	@PostMapping(value="/getDoctorInfoFromSecureLogin")
	public DoctorGetDto getPatientInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return doctorRepository.getDoctorInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updateDoctorInfoBySecureLogin")
	public boolean updateDoctorInfoBySecureLogin(@RequestBody final Doctor doctor) {
		if(userRepository.existsByUserUsername(doctor.getUserUsername())) {
			if(userRepository.findUserNameBySecureLogin(doctor.getUserSecureLogin()).equals(doctor.getUserUsername())){ 
				doctorRepository.updateDoctorInfoBySecureLogin(doctor.getUserSecureLogin(),doctor.getUserUsername(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getUserCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender());
				  return true;
				}else
				  return false;
		}
		else {
			doctorRepository.updateDoctorInfoBySecureLogin(doctor.getUserSecureLogin(),doctor.getUserUsername(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getUserCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender());
			return true;
		}
	}

	@GetMapping(value="getPendingDoctorsNumber")
	public Long getPendingDoctorsNumber() {
		return doctorRepository.getPendingDoctorsNumber();
	}
	
	@GetMapping(value="/getPendingDoctors/{page}/{size}")
	public List<PendingDoctorGetDto> getPendingDoctors(@PathVariable("page") final Integer page,@PathVariable("size") final Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("user_id"));
		return doctorRepository.getPendingDoctors(pageable);
	}

	@PostMapping(value="changeDoctorStatusBySecureLogin")
	public boolean changeDoctorStatusBySecureLogin(@RequestBody final TwoStrings twoStrings) {
		if("approved".equals(twoStrings.getStringTwo())) {
			
			long userId = doctorRepository.getDoctorIdFromSecureLogin(twoStrings.getStringOne());
			
			Notification notification = new Notification();
			notification.setNotificationType("setYourGeoLocation");
			notification.setSenderId(-1);
			notification.setRecipientId(userId);
			notificationController.add(notification);
			
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("notification");
			webSocketNot.setData("");
			webSocketNot.setNotification(notification);
			template.convertAndSend("/topic/notification/"+userId,webSocketNot);
		}
		doctorRepository.changeDoctorStatusBySecureLogin(twoStrings.getStringOne(),twoStrings.getStringTwo());
		return true;
	}
	
	@PostMapping(value="changeDoctorStatusById")
	public boolean changeDoctorStatusById(@RequestBody final IntegerAndString integerAndString) {
		if("approvedByAdmin".equals(integerAndString.getString()) || "disapprovedByAdmin".equals(integerAndString.getString()) || "disapprovedPermanently".equals(integerAndString.getString())) {
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("info");
			webSocketNot.setData(integerAndString.getString());
			template.convertAndSend("/topic/notification/"+integerAndString.getInteger(),webSocketNot);
		}
		doctorRepository.changeDoctorStatusById(integerAndString.getInteger() ,integerAndString.getString());
		return true;
	}
	
	@PostMapping(value="updateDoctorSettingsBySecurelogin")
	public boolean updateDoctorSettingsBySecurelogin(@RequestBody final DoctorSettingsDto doctorSettingsDto) {
		doctorRepository.updateDoctorSettingsBySecurelogin(doctorSettingsDto.getSecureLogin(),doctorSettingsDto.getMaxPatientPerDay(),doctorSettingsDto.getStartTime(),doctorSettingsDto.getExactAddress(),doctorSettingsDto.getWorkDays(),doctorSettingsDto.getAppointmentPrice(),doctorSettingsDto.getAppointmentApproximateDuration());
		return true;
	}
	
	@DeleteMapping(value="deteleDoctorById/{id}")
	@Transactional
	public long deteleDoctorById(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return doctorRepository.deleteByUserId(id);
	}
	
	@PostMapping(value="/getDoctorSpecialitiesBySecureLogin")
	public List<String> getDoctorSpecialitiesBySecureLogin(@RequestBody final OneString secureLogin) {
		return specialityRepository.getDoctorSpecialitiesBySecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/getApprovedDoctorsBySpecialityIdAndCity")
	public List<SearchedDoctorDto> getApprovedDoctorsBySpecialityIdAndCity(@RequestBody final SearchedDocDto search) {
		Pageable pageable = PageRequest.of(search.getPage(), search.getSize(), Sort.by("doctor_rate").descending());
		String city = search.getDoctorCity().toLowerCase();
		city = city.replace("é", "e");
		city = city.replace("è", "e");
		if("whole tunisia".equals(city) || "toute la tunisie".equals(city)) {
			return doctorRepository.getApprovedDoctorsBySpecialityId(search.getSpecialityCode(),pageable);
		}else {
			return doctorRepository.getApprovedDoctorsBySpecialityIdAndCity(search.getSpecialityCode(),city,pageable);
		}
		
	}
	
	@GetMapping(value="getDoctorAppointmentInfoByDoctorId/{id}")
	public AppointmentDocInfo getDoctorAppointmentInfoByDoctorId(@PathVariable("id") Integer id) {
		return doctorRepository.getDoctorAppointmentInfoByDoctorId(id);
	}
	
	@GetMapping(value="getAppPatientInfoById/{id}")
	public AppointmentPatientInfo getAppPatientInfoById(@PathVariable(name="id") Long id) {
		return patientRepository.getAppPatientInfoById(id);
	}
	
	@PostMapping(value="getAppPatientInfoByDoctorIdTurnAndDate")
	public CurrentPatientInfo getAppPatientInfoByDoctorIdTurnAndDate(@RequestBody final IdTurnAndDate data) {
		return patientRepository.getAppPatientInfoByDoctorIdTurnAndDate(data.getId(),data.getDate(),data.getTurn());
	}

	@PostMapping(value="changeCurrentPatientBySecureLogin")
	public boolean changeCurrentPatientBySecureLogin(@RequestBody final SecureLoginAndPatientTurnDto data) {
		
		doctorRepository.changeCurrentPatientBySecureLogin(data.getSecureLogin(),data.getPatientTurn());
		
		if(data.getPatientTurn() != 0) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			
			if(data.getPatientTurn() == 1) {
				
				Notification notStartSession = new Notification();
				WebSocketNotificationDto webSocketNotStartSession = new WebSocketNotificationDto();
				
				
				for(int p = 1;p <= data.getAllPatientNumber() ;p++) {
					
					AppUsersInfoDto usersInfo = appointmentRepository.getUsersInfoByAppDayAndTurnAndDocSecureLogin(dateFormat.format(cal.getTime()),p,data.getSecureLogin());

					notStartSession.setIsUnread(true);
					notStartSession.setNotificationType("doctorStartSession");
					notStartSession.setRecipientId(usersInfo.getPatientId());
					notStartSession.setSenderId(usersInfo.getDoctorId());
					notificationController.add(notStartSession);
						
					webSocketNotStartSession.setData(usersInfo.getDoctorFirstName()+" "+usersInfo.getDoctorLastName());
					webSocketNotStartSession.setType("notification");
					webSocketNotStartSession.setNotification(notStartSession);

					template.convertAndSend("/topic/notification/"+usersInfo.getPatientId(),webSocketNotStartSession);
				}
			}
			
			for (int i=data.getPatientTurn(); i <= (data.getPatientTurn() +4);i++) {
				
				Notification notTurnClose = new Notification();
				WebSocketNotificationDto webSocketNotTurnClose = new WebSocketNotificationDto();
				
				
				if(i<=data.getAllPatientNumber()) {
					
					AppUsersInfoDto usersInfo = appointmentRepository.getUsersInfoByAppDayAndTurnAndDocSecureLogin(dateFormat.format(cal.getTime()),i,data.getSecureLogin());
				
					notTurnClose.setIsUnread(true);
					notTurnClose.setNotificationType("patientTurnClose");
					notTurnClose.setRecipientId(usersInfo.getPatientId());
					notTurnClose.setSenderId(usersInfo.getDoctorId());
					notTurnClose.setNotificationParameter(data.getPatientTurn()+"");
					notificationController.add(notTurnClose);
						
					webSocketNotTurnClose.setData(usersInfo.getDoctorFirstName()+" "+usersInfo.getDoctorLastName());
					webSocketNotTurnClose.setType("notification");
					webSocketNotTurnClose.setNotification(notTurnClose);

					template.convertAndSend("/topic/notification/"+usersInfo.getPatientId(),webSocketNotTurnClose);
				}else
					break;
			}
		}
		
		return true;
	}
	
	@GetMapping(value="getDoctorInfoByDoctorId/{id}")
	public SearchedDoctorDto getDoctorInfoByDoctorId(@PathVariable(name="id") Long id) {
		return doctorRepository.getDoctorInfoByDoctorId(id);
	}
	
	@PostMapping(value="/getTopRatedDoctor")
	public List<TopRatedDoctorsDto> getTopRatedDoctor(@RequestBody final PageableDto search) {
		Pageable pageable = PageRequest.of(search.getPage(), search.getSize(), Sort.by("doctor_rate").descending());
		return doctorRepository.getTopRatedDoctor(pageable);
	}
	
	@PostMapping(value="/updatePositionBySecureLogin")
	public boolean updatePositionBySecureLogin(@RequestBody final UpdatePositionDto data) {
		doctorRepository.updatePositionBySecureLogin(data.getSecureLogin(),data.getLatitude(),data.getLongitude());
		return true;
	}
	
	
}

