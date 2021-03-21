package com.healthCare.healthCareDataBase.Resource;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Doctor;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.SpecialityRepository;

import dtos.AppointmentDocInfo;
import dtos.FiveStrings;
import dtos.GetApprovedDoctors;
import dtos.OneString;
import dtos.IntegerAndString;
import dtos.TwoStrings;
import dtos.UsernameAndPassDto;

@CrossOrigin
@RestController
@RequestMapping(value="/doctor")
public class DoctorResource {
	
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	SpecialityRepository specialityRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping(value="/all")
	public List<Doctor>getAll(){
		return doctorRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Doctor doctor) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		doctor.setDoctorCreationDate(dateFormat.format(cal.getTime()));
		doctor.setDoctorStatus("notApproved");
		doctorRepository.save(doctor);
		return"userCreated";
	}
	
	@PostMapping(value="/addspeciality")
	public String addSpeciality(@RequestBody final IntegerAndString integerAndString) {
		Integer specId = specialityRepository.getSpecialityIdBySpecialityCode(integerAndString.getString());
		if(specialityRepository.checkIfDoctorAlreadyHaveTheSpeciality(integerAndString.getInteger(),specId)==integerAndString.getInteger())
			return "doctorAlreadyHaveSpeciality";
		else {
			specialityRepository.addSpecialityToDoctor(integerAndString.getInteger(),specId);
			return "specialityAdded";
		}
	}
	
	@PostMapping(value="/getDoctorSecureLoginFromUsernameAndPass")
	public String getDoctorSecureLoginFromUsernameAndPass(@RequestBody final UsernameAndPassDto usernameAndPass) {
		Integer docId = doctorRepository.getDoctorIdFromUsernameAndPass(usernameAndPass.getUsername(),usernameAndPass.getPassword());
		if(docId==null) {
			return "invalidInfo";
		}else {
			String secureLogin=secureString(25);
			while(doctorRepository.existsByDoctorSecureLogin(secureLogin)||patientRepository.existsByPatientSecureLogin(secureLogin)||pharmacyRepository.existsByPharmacySecureLogin(secureLogin)||adminRepository.existsByAdminSecureLogin(secureLogin)) {
				secureLogin=secureString(25);
			}
			doctorRepository.getDoctorSecureLoginFromId(docId, secureLogin);
			return secureLogin;
		}	
	}
	
	@PostMapping(value="/getDoctorInfoFromSecureLogin")
	public Doctor getPatientInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return doctorRepository.getDoctorInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updateDoctorInfoBySecureLogin")
	public String updateDoctorInfoBySecureLogin(@RequestBody final Doctor doctor) {
		if(patientRepository.existsByPatientUserName(doctor.getDoctorUserName()) || pharmacyRepository.existsByPharmacyUserName(doctor.getDoctorUserName()) || doctorRepository.existsByDoctorUserName(doctor.getDoctorUserName())|| adminRepository.existsByAdminUserName(doctor.getDoctorUserName())) {
			if(doctorRepository.findUserNameBySecureLogin(doctor.getDoctorSecureLogin()).equals(doctor.getDoctorUserName())){ 
				doctorRepository.updatePatientInfoBySecureLogin(doctor.getDoctorSecureLogin(),doctor.getDoctorUserName(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getDoctorCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender(),doctor.getDoctorPassword());
				  return "updated";
				}else
				  return "usernameExist";
		}
		else {
			doctorRepository.updatePatientInfoBySecureLogin(doctor.getDoctorSecureLogin(),doctor.getDoctorUserName(), doctor.getDoctorFirstName(),doctor.getDoctorLastName(),doctor.getDoctorCity(),doctor.getDoctorBirthDay(),doctor.getDoctorGender(),doctor.getDoctorPassword());
			return "updated";
		}
	}
	
	public String secureString(int len){
		 String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz&é(-/+*)=}@à^ç_è[]{#";
		 SecureRandom rnd = new SecureRandom();
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
		   }

	@GetMapping(value="/getPendingDoctors")
	public List<Doctor> getPendingDoctors() {
		return doctorRepository.getPendingDoctors();
	}

	@PostMapping(value="changeDoctorStatusBySecureId")
	public String changeDoctorStatusBySecureId(@RequestBody final TwoStrings twoStrings) {
		doctorRepository.changeDoctorStatusBySecureId(twoStrings.getStringOne(),twoStrings.getStringTwo());
		return "doctorStatusUpdated";
	}
	
	@PostMapping(value="changeDoctorStatusById")
	public String changeDoctorStatusById(@RequestBody final TwoStrings twoStrings) {
		doctorRepository.changeDoctorStatusById(Integer.parseInt(twoStrings.getStringOne()) ,twoStrings.getStringTwo());
		return "doctorStatusUpdated";
	}
	
	@PostMapping(value="updateDoctorSettingsBySecurelogin")
	public String updateDoctorSettingsBySecurelogin(@RequestBody final FiveStrings fiveStrings) {
		doctorRepository.updateDoctorSettingsBySecurelogin(fiveStrings.getString1(),fiveStrings.getString2(),fiveStrings.getString3(),fiveStrings.getString4(),fiveStrings.getString5());
		return "updated";
	}
	
	@DeleteMapping(value="deteleDoctorById/{id}")
	@Transactional
	public long deteleDoctorById(@PathVariable("id") Integer id) {
		return doctorRepository.deleteByDoctorId(id);
	}
	
	@GetMapping(value="/getApprovedDoctorsBySpecialityId/{specialityId}")
	public List<GetApprovedDoctors> getApprovedDoctorsBySpecialityId(@PathVariable("specialityId") Integer specialityId) {
		return doctorRepository.getApprovedDoctorsBySpecialityId(specialityId);
	}
	
	@GetMapping(value="getDoctorAppointmentInfoByDoctorId/{id}")
	public AppointmentDocInfo getDoctorAppointmentInfoByDoctorId(@PathVariable("id") Integer id) {
		return doctorRepository.getDoctorAppointmentInfoByDoctorId(id);
	}
}

