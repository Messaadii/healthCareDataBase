package com.healthCare.healthCareDataBase.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.AppointmentInfoForPatient;
import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.GetHeightValuesDto;
import com.healthCare.healthCareDataBase.Dtos.GetHeightValuesRequestDto;
import com.healthCare.healthCareDataBase.Dtos.GetMyUsersRequestDto;
import com.healthCare.healthCareDataBase.Dtos.GetMyUsersWithPag;
import com.healthCare.healthCareDataBase.Dtos.GetWeightValuesDto;
import com.healthCare.healthCareDataBase.Dtos.OneString;
import com.healthCare.healthCareDataBase.Dtos.PatientGetDto;
import com.healthCare.healthCareDataBase.Dtos.UpdateMedicalProfileIdRequest;
import com.healthCare.healthCareDataBase.Model.OldData;
import com.healthCare.healthCareDataBase.Model.Patient;
import com.healthCare.healthCareDataBase.Repository.AdminRepository;
import com.healthCare.healthCareDataBase.Repository.DoctorRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileDiseaseRepository;
import com.healthCare.healthCareDataBase.Repository.MedicalProfileRepository;
import com.healthCare.healthCareDataBase.Repository.PatientRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/patient")
public class PatientController {
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	@Autowired
	MedicalProfileRepository medicalProfileRepository;
	@Autowired
	MedicalProfileDiseaseRepository medicalProfileDiseaseRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	OldDataController oldDataController;
	
	@GetMapping(value="/all")
	public List<Patient>getAll(){
		return patientRepository.findAll();
	}
	
	@GetMapping(value="/getPatientIdFromUsernameAndPassword/{username}/{password}")
	public Integer getPatientIdFromUsernameAndPassword(@PathVariable(name="username") String username,@PathVariable(name="password") String password) {
		return patientRepository.getPatientIdFromUsernameAndPass(username,password);
	}
	
	@PostMapping(value="/getPatientInfoFromSecureLogin")
	public PatientGetDto getPatientInfoFromSecureLogin(@RequestBody final OneString secureLogin) {
		return patientRepository.getPatientInfoFromSecureLogin(secureLogin.getOne());
	}
	
	@PostMapping(value="/updatePatientInfoBySecureLogin")
	public boolean updatePatientInfoBySecureLogin(@RequestBody final Patient patient) {
			patientRepository.updatePatientInfoBySecureLogin(patient.getUserSecureLogin(), patient.getPatientFirstName(),patient.getPatientLastName(),patient.getUserCity(),patient.getPatientBirthDay(),patient.getPatientGender());
			return true;
	}
	
	@PostMapping(value="/updateMedicalProfileByMedicalProfileId")
	public boolean updateMedicalProfileBySecureLogin(@RequestBody final UpdateMedicalProfileIdRequest medicalProfile) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();

		boolean returnBol = false;
		
		try {
			Date date1 = myFormat.parse(myFormat.format(cal.getTime()));
			String oldDate = medicalProfileRepository.getLastUpdate(medicalProfile.getId());
			Date date2 = myFormat.parse(oldDate != null ? oldDate.substring(0,10) : "1999/11/18");
			long diff = -1;
			if(oldDate != null)
				diff = date1.getTime() - date2.getTime();
		    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 7 || diff == -1) {
		    	double oldHeight = medicalProfileRepository.getHeight(medicalProfile.getId());
				double oldWeight = medicalProfileRepository.getWeight(medicalProfile.getId());
				
				OldData newHeight = new OldData(medicalProfile.getId(),"height",oldHeight+"");
				oldDataController.add(newHeight);
				
				OldData newWeight = new OldData(medicalProfile.getId(),"weight",oldWeight+"");
				oldDataController.add(newWeight);
				
				medicalProfileRepository.updateHeightAndWeightByMedicalProfileId(medicalProfile.getId(),medicalProfile.getHeight(),medicalProfile.getWeight());
				returnBol = true;
		    } else
		    	returnBol = false;
		} catch (java.text.ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			returnBol = false;
		}
		
		return returnBol;
	}
	
	@GetMapping(value="getDoctorAppointmentInfoForPatientByDoctorId/{id}")
	public AppointmentInfoForPatient getDoctorAppointmentInfoForPatientByDoctorId(@PathVariable("id") Long id) {
		return doctorRepository.getDoctorAppointmentInfoForPatientByDoctorId(id);
	}
	
	@GetMapping(value="getUserFullNameById/{id}")
	public FirstAndLastNameDto getUserFullNameById(@PathVariable("id") Long id) {
		return patientRepository.getUserFullNameById(id);
	}

	@PostMapping(value="getMyDoctors")
	public GetMyUsersWithPag getMyDoctors(@RequestBody final GetMyUsersRequestDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
		return new GetMyUsersWithPag (patientRepository.getMyDoctors(data.getSecureLogin(),pageable),
				data.getPage() == 0 ? (patientRepository.getMyDoctorsNumber(data.getSecureLogin()) != null ? patientRepository.getMyDoctorsNumber(data.getSecureLogin()) : 0) : 0);
	}
	
	@PostMapping(value="getMySecretaries")
	public GetMyUsersWithPag getMySecretaries(@RequestBody final GetMyUsersRequestDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
		return new GetMyUsersWithPag (patientRepository.getMySecretaries(data.getSecureLogin(),pageable),
				data.getPage() == 0 ? (patientRepository.getMySecretariesNumber(data.getSecureLogin()) != null ? patientRepository.getMySecretariesNumber(data.getSecureLogin()) : 0) : 0);
	}
	
	@PostMapping(value="getMyPharmacies")
	public GetMyUsersWithPag getMyPharmacies(@RequestBody final GetMyUsersRequestDto data){
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
		
		return new GetMyUsersWithPag (patientRepository.getMyPharmacies(data.getSecureLogin(),pageable),
				data.getPage() == 0 ? (patientRepository.getMyPharmaciesNumber(data.getSecureLogin()) != null ? patientRepository.getMyPharmaciesNumber(data.getSecureLogin()) : 0) : 0);
	}
	
	@PostMapping(value="getHeightValues")
	public List<GetHeightValuesDto> getHeightValues(@RequestBody final GetHeightValuesRequestDto data){
		List<GetHeightValuesDto> list =patientRepository.getHeightValues(data.getSecureLogin());

		list.add(patientRepository.getCurrentHeight(data.getSecureLogin()));
		
		return list;
	}
	
	@PostMapping(value="getWeightValues")
	public List<GetWeightValuesDto> getWeightValues(@RequestBody final GetHeightValuesRequestDto data){
		List<GetWeightValuesDto> list =patientRepository.getWeightValues(data.getSecureLogin());

		list.add(patientRepository.getCurrentWeight(data.getSecureLogin()));
		
		return list;
	}
}
