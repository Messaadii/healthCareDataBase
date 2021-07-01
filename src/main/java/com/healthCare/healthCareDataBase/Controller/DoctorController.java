package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.healthCare.healthCareDataBase.Dtos.AddSecretaryRequestDto;
import com.healthCare.healthCareDataBase.Dtos.AppUsersInfoDto;
import com.healthCare.healthCareDataBase.Dtos.AppointmentDocInfo;
import com.healthCare.healthCareDataBase.Dtos.AppointmentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.CheckSecretaryCodeRequestDto;
import com.healthCare.healthCareDataBase.Dtos.CurrentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.DoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.DoctorSettingsDto;
import com.healthCare.healthCareDataBase.Dtos.GetMySecretariesDto;
import com.healthCare.healthCareDataBase.Dtos.GetSecretaryWorkRequestDto;
import com.healthCare.healthCareDataBase.Dtos.GetUserIdDto;
import com.healthCare.healthCareDataBase.Dtos.IdTurnAndDate;
import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PageableDto;
import com.healthCare.healthCareDataBase.Dtos.PendingDoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDocDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDoctorDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryAllInfoDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoForDoctorDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryPublicInfoDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryWorkDto;
import com.healthCare.healthCareDataBase.Dtos.SecureLoginAndPatientTurnDto;
import com.healthCare.healthCareDataBase.Dtos.SendMailDto;
import com.healthCare.healthCareDataBase.Dtos.SignupRequestDto;
import com.healthCare.healthCareDataBase.Dtos.TopRatedDoctorsDto;
import com.healthCare.healthCareDataBase.Dtos.UpdatePositionDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Doctor;
import com.healthCare.healthCareDataBase.Model.ERole;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Model.Role;
import com.healthCare.healthCareDataBase.Model.Secretary;
import com.healthCare.healthCareDataBase.Model.SecretaryWork;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.AppointmentRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.RoleRepository;
import com.healthCare.healthCareDataBase.Repository.SecretaryRepository;
import com.healthCare.healthCareDataBase.Repository.SecretaryWorkRepository;
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
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SecretaryRepository secretaryRepository;
	
	@Autowired
	SecretaryWorkRepository secretaryWorkRepository;
	
	@Autowired
	SecretaryWorkController secretaryWorkController;
	
	@Autowired
	SendEmailController sendEmailController;
	
	@Autowired
	PasswordEncoder encoder;

	
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
	
	@PostMapping(value="/getDoctorInfoById")
	public DoctorGetDto getDoctorInfoById(@RequestBody final GetUserIdDto data) {
		return doctorRepository.getDoctorInfoById(data.getUserId());
	}
	
	@PostMapping(value="/updateDoctorInfoById")
	public boolean updateDoctorInfoById(@RequestBody final Doctor doctor) {
		if(userRepository.existsByUserUsername(doctor.getUserUsername())) {
			if(userRepository.findUserNameByUserId(doctor.getUserId()).equals(doctor.getUserUsername())){ 
				doctorRepository.updateDoctorInfoById(doctor.getUserId(),doctor.getUserUsername(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getUserCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender());
				  return true;
				}else
				  return false;
		}
		else {
			doctorRepository.updateDoctorInfoById(doctor.getUserId(),doctor.getUserUsername(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getUserCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender());
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
	
	@PostMapping(value="updateDoctorSettingsById")
	public boolean updateDoctorSettingsById(@RequestBody final DoctorSettingsDto doctorSettingsDto) {
		doctorRepository.updateDoctorSettingsById(doctorSettingsDto.getUserId(),doctorSettingsDto.getMaxPatientPerDay(),doctorSettingsDto.getStartTime(),doctorSettingsDto.getExactAddress(),doctorSettingsDto.getWorkDays(),doctorSettingsDto.getAppointmentPrice(),doctorSettingsDto.getAppointmentApproximateDuration());
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

	@PostMapping(value="changeCurrentPatientById")
	public boolean changeCurrentPatientById(@RequestBody final SecureLoginAndPatientTurnDto data) {
		
		doctorRepository.changeCurrentPatientById(data.getDoctorId(),data.getPatientTurn());
		
		WebSocketNotificationDto webSocketNotPos = new WebSocketNotificationDto();
		webSocketNotPos.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
		webSocketNotPos.setType("nextPatient");
		webSocketNotPos.setExtraData(data.getPatientTurn()+"");
		
		List<Long> secretaries = appointmentRepository.getDoctorSecretariesById((int)data.getDoctorId());
		for(int i =0;i<secretaries.size();i++) {
			template.convertAndSend("/topic/notification/"+secretaries.get(i),webSocketNotPos);
		}
		
		if(data.getPatientTurn() != 0) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			
			if(data.getPatientTurn() == 1) {
				
				Notification notStartSession = new Notification();
				WebSocketNotificationDto webSocketNotStartSession = new WebSocketNotificationDto();
				
				
				for(int p = 1;p <= data.getAllPatientNumber() ;p++) {
					
					AppUsersInfoDto usersInfo = appointmentRepository.getUsersInfoByAppDayAndTurnAndDocDocId(dateFormat.format(cal.getTime()),p,data.getDoctorId());

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
					
					AppUsersInfoDto usersInfo = appointmentRepository.getUsersInfoByAppDayAndTurnAndDocDocId(dateFormat.format(cal.getTime()),i,data.getDoctorId());
				
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
	
	@PostMapping(value="/updatePositionById")
	public boolean updatePositionById(@RequestBody final UpdatePositionDto data) {
		doctorRepository.updatePositionById(data.getUserId(),data.getLatitude(),data.getLongitude());
		return true;
	}
	
	@PostMapping(value="/sendSeeSecretaryWorkRequest")
	public String sendSeeSecretaryWorkRequest(@RequestBody final AddSecretaryRequestDto data) {
		
		long secretaryId = doctorRepository.getSecretaryIdByEmail(data.getEmail());
		
		if(secretaryId == 0)
			return "notFound";
		else {
			if(doctorRepository.getSecretaryDoctor(secretaryId) == data.getDoctorId())
				return "alreadyWithYou";
			else {
				long notificationId = doctorRepository.checkIfNotificationAlreadyAdded(data.getDoctorId(),secretaryId,"seeSecretaryWorkRequest");
				Notification not = new Notification();
				WebSocketNotificationDto webSocket = new WebSocketNotificationDto();
				
				int verifCode = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
				not.setIsUnread(true);
				not.setNotificationType("seeSecretaryWorkRequest");
				not.setRecipientId(secretaryId);
				not.setSenderId(data.getDoctorId());
				not.setNotificationParameter(verifCode+"");
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				not.setTimeSent(dateFormat.format(cal.getTime()));
				
				if(notificationId == 0)
					notificationController.add(not);
				else 
					doctorRepository.updateNotificationById(data.getDoctorId(),verifCode,dateFormat.format(cal.getTime()),notificationId);
				
					
				webSocket.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
				webSocket.setType("notification");
				webSocket.setNotification(not);
				
				template.convertAndSend("/topic/notification/"+secretaryId,webSocket);
				
				return "found";
			}
		}
	}
	
	@PostMapping(value="/addSecretary")
	public boolean addSecretary(@RequestBody final AddSecretaryRequestDto data) {
		long secretaryId = doctorRepository.getSecretaryIdByEmail(data.getEmail());
		
		Notification not = new Notification();
		WebSocketNotificationDto webSocket = new WebSocketNotificationDto();
		
		not.setIsUnread(true);
		not.setNotificationType("doctorAddedYou");
		not.setRecipientId(secretaryId);
		not.setSenderId(data.getDoctorId());
		not.setNotificationParameter("pending");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		not.setTimeSent(dateFormat.format(cal.getTime()));
		
		long notificationId = doctorRepository.checkIfNotificationAlreadyAdded(data.getDoctorId(),secretaryId,"doctorAddedYou");
		
		if(notificationId == 0)
			notificationController.add(not);
		else 
			doctorRepository.updateDoctorAddedYouNotificationById(dateFormat.format(cal.getTime()),notificationId);
			
		webSocket.setData(userRepository.getUsernameByUserid(data.getDoctorId()));
		webSocket.setType("notification");
		webSocket.setNotification(not);
		
		template.convertAndSend("/topic/notification/"+secretaryId,webSocket);
		return true;
	}
	
	@PostMapping(value="/createSecretaryAccount")
	public boolean createSecretaryAccount(@RequestBody SignupRequestDto data) throws MessagingException {
		
		 String userType = userRepository.getUserTypeByUsername(data.getUsername());
		
		if(userType.length() != 0)
			return false;
		else {
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.SECRETARY_ROLE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			
			SendMailDto mail=new SendMailDto("verification","",data.getUsername());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			
			SendMailDto mailInfo=new SendMailDto("verification","",data.getUsername());
			
			Secretary secretary = new Secretary(data.getUsername(),encoder.encode(data.getUserPassword()),data.getUserCity(),
					roles,dateFormat.format(cal.getTime()),data.getUserFirstName(),data.getUserLastName(),
					data.getUserBirthday(),data.getUserGender(),0,data.getDoctorId(),sendEmailController.sendEmail(mailInfo)+"");
			
			secretaryRepository.save(secretary);
			
			SecretaryWork secretaryWork = new SecretaryWork(secretary.getUserId(),secretary.getDoctorId(),dateFormat.format(cal.getTime()),null);
			
			secretaryWorkController.add(secretaryWork);
			
			return true;
		}
	}
	
	@PostMapping(value="getMySecretaries")
	public List<SecretaryPublicInfoDto> getMySecretaries(@RequestBody final GetMySecretariesDto data){
		return doctorRepository.getMySecretaries(data.getDoctorId());
	}
	
	@PostMapping(value="getSecretaryWorkById")
	public List<SecretaryWorkDto> getSecretaryWorkById(@RequestBody final GetSecretaryWorkRequestDto data){
		return doctorRepository.getSecretaryWorkById(data.getSecretaryId());
	}
	
	@PostMapping(value="checkSecretaryCode")
	public SecretaryAllInfoDto checkSecretaryCode(@RequestBody final CheckSecretaryCodeRequestDto data) {
		
		SecretaryInfoForDoctorDto secretary = doctorRepository.checkSecretaryCode(data.getDoctorId(),data.getEmail(),data.getCode());
		
		if(secretary != null)
			return new SecretaryAllInfoDto(secretary.getSecretaryFirstName(),secretary.getSecretaryLastName(),
				secretary.getSecretaryGender(),secretary.getSecretaryRate(),secretary.getSecretaryBirthDay(),secretary.getUserCity(),
				secretary.getUserId(),doctorRepository.getSecretaryWorkForDocById(secretary.getUserId()));
		else
			return null;
		
	}
}

