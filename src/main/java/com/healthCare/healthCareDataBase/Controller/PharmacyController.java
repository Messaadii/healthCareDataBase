package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PageableDto;
import com.healthCare.healthCareDataBase.Dtos.PendingPharmcyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacySettingsDto;
import com.healthCare.healthCareDataBase.Dtos.UpdatePositionDto;
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
		if("approved".equals(data.getString())) {
			Notification notification = new Notification();
			notification.setNotificationType("setYourGeoLocation");
			notification.setSenderId(-1);
			notification.setRecipientId(data.getInteger());
			notificationController.add(notification);
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
}
