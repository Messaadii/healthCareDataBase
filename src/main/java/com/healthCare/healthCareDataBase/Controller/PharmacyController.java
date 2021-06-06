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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.FindPharmacyGet;
import com.healthCare.healthCareDataBase.Dtos.FindPresDto;
import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.PageableDto;
import com.healthCare.healthCareDataBase.Dtos.PendingPharmcyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacySettingsDto;
import com.healthCare.healthCareDataBase.Dtos.PrescriptionForPharmacyDto;
import com.healthCare.healthCareDataBase.Dtos.PrescriptionForPharmacyWithPageableDto;
import com.healthCare.healthCareDataBase.Dtos.SearchPhPresByIdAndNameDto;
import com.healthCare.healthCareDataBase.Dtos.UpdatePositionDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Model.Pharmacy;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationController notificationController;
	@Autowired
    private SimpMessagingTemplate template;
	
	@GetMapping(value="/all")
	public List<Pharmacy>getAll(){
		return pharmacyRepository.findAll();
	}
	
	@GetMapping(value="/getPharmacyIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPharmacyIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return pharmacyRepository.getPharmacyIdFromUsernameAndPass(username,password);
	}
	
	@PostMapping(value="/getPharmacyInfoFromSecureLogin")
	public PharmacyGetDto getPharmacyInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return pharmacyRepository.getPharmacyInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updatePharmacyInfoBySecureLogin")
	public boolean updatePharmacyInfoBySecureLogin(@RequestBody final Pharmacy pharmacy) {
		pharmacyRepository.updatePharmacyInfoBySecureLogin(pharmacy.getUserSecureLogin(), pharmacy.getPharmacyFullName(),pharmacy.getUserCity());
			return true;
	}
	
	@PostMapping(value="changePharamcyStatusAndSettingsBySecureLogin")
	public boolean changePharamcyStatusAndSettingsBySecureLogin(@RequestBody final PharmacySettingsDto data) {
		pharmacyRepository.changePharamcyStatusAndSettingsBySecureLogin(data.getSecureLogin(),data.getStatus(),data.getExactAddress(),data.getAccountType());
		return true;
	}
	
	@PostMapping(value="getPendingPharmacies")
	public List<PendingPharmcyGetDto> getPendingPharmacies(@RequestBody final PageableDto data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("user_id"));
		return pharmacyRepository.getPendingPharmacies(pageable);
	}
	
	@PostMapping(value="changePharmacyStatusById")
	public boolean changePharmacyStatusById (@RequestBody final IntegerAndString data) {
		pharmacyRepository.changePharmacyStatusById(data.getInteger(),data.getString());
		if("approvedByAdmin".equals(data.getString()) || "disapprovedByAdmin".equals(data.getString()) || "disapprovedPermanently".equals(data.getString())) {
			if("approved".equals(data.getString())) {
				Notification notification = new Notification();
				notification.setNotificationType("setYourGeoLocation");
				notification.setSenderId(-1);
				notification.setRecipientId(data.getInteger());
				notificationController.add(notification);
				
				WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
				webSocketNot.setType("notification");
				webSocketNot.setData("");
				webSocketNot.setNotification(notification);
				template.convertAndSend("/topic/notification/"+data.getInteger(),webSocketNot);
			}else {
				WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
				webSocketNot.setType("info");
				webSocketNot.setData(data.getString());
				template.convertAndSend("/topic/notification/"+data.getInteger(),webSocketNot);
			}
		}
		return true;
	}
	
	@GetMapping(value="deleteByUserId/{id}")
	public boolean deleteByUserId(@PathVariable("id") final Long id) {
		pharmacyRepository.deleteById(id);
		return true;
	}
	
	@PostMapping(value="/updatePositionBySecureLogin")
	public boolean updatePositionBySecureLogin(@RequestBody final UpdatePositionDto data) {
		pharmacyRepository.updatePositionBySecureLogin(data.getSecureLogin(),data.getLatitude(),data.getLongitude());
		return true;
	}
	
	@PostMapping(value="findPharmacyByPrescriptonMedicamentAndGeoLocation")
	public List<FindPharmacyGet> findPharmacyByPrescriptonMedicamentAndGeoLocation(@RequestBody final FindPresDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
		if(data.getSearchRaduis()==0)
			data.setSearchRaduis(5000);
		return pharmacyRepository.findPharmacyByPrescriptonMedicamentAndGeoLocation(data.getMedicamentsName(),data.getUserLatitude(),data.getUserLongitude(),data.getSearchRaduis(),data.getMedicamentsName().length,pageable);
	}
	
	@GetMapping(value="getPharmacyInfoById/{id}")
	public PharmacyGetDto getPharmacyInfoById(@PathVariable final long id) {
		return pharmacyRepository.getPharmacyInfoById(id);
	}
	
	@GetMapping(value="getTodayPrescriptionNumberById/{id}")
	public long getTodayPrescriptionNumberById(@PathVariable final long id) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		return pharmacyRepository.getTodayPrescriptionNumberById(id,"%"+dateFormat.format(cal.getTime())+"%");
	}
	
	@PostMapping(value="getPharmacyPrescriptionsById")
	public PrescriptionForPharmacyWithPageableDto getPharmacyPrescription(@RequestBody final PageableAndIdDto data) {
		
		List<PrescriptionForPharmacyDto> list = pharmacyRepository.getPharmacyPrescriptionsById(data.getId(),
				PageRequest.of(data.getPage(), data.getSize(), Sort.by("time_sent").descending()));
		
		PrescriptionForPharmacyWithPageableDto returnObject = new PrescriptionForPharmacyWithPageableDto(
				data.getPage() == 0 ? pharmacyRepository.countGetPharmacyPrescriptionsById(data.getId()) : 0,
				list);
		
		return returnObject;
	}
	
	@PostMapping(value="searchPharamacyPrescriptionByPatientName")
	public PrescriptionForPharmacyWithPageableDto searchPharamacyPrescriptionByPatientName(@RequestBody final SearchPhPresByIdAndNameDto data) {
		
		List<PrescriptionForPharmacyDto> list = pharmacyRepository.searchPharamacyPrescriptionsByPatientName(data.getName(),data.getId(),
				PageRequest.of(data.getPage(), data.getSize(), Sort.by("time_sent").descending()));
		
		PrescriptionForPharmacyWithPageableDto returnObject = new PrescriptionForPharmacyWithPageableDto(
				data.getPage() == 0 ? pharmacyRepository.countSearchPharamacyPrescriptionsByPatientName(data.getName(),
						data.getId()) : 0,list);
		
		return returnObject;
	}
	
}
