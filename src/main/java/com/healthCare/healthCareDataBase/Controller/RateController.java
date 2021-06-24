package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Rate;
import com.healthCare.healthCareDataBase.Repository.RateRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/rate")
public class RateController {
	
	@Autowired
	RateRepository rateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value="add")
	public double add(@RequestBody Rate rate) {
		if(rateRepository.checkIfRateAlreadyAdded(rate.getRatedBy(),rate.getRateTo())) {
			rateRepository.updateRate(rate.getRatedBy(),rate.getRateTo(),rate.getRate());
		}else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			rate.setRateTime(dateFormat.format(cal.getTime()));
			rateRepository.save(rate);
		}
		
		double newRate = rateRepository.getUserRate(rate.getRateTo());
		String userType = userRepository.getUserTypeByUserId(rate.getRateTo());
		
		if(newRate == 5) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),5);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),5);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),5);
			
			return 5;
		}
		else if(newRate >= 4.5) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),4.5);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),4.5);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),4.5);
			
			return 4.5;
		}
		else if(newRate >= 4) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),4);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),4);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),4);
			
			return 4;
		}
		else if(newRate >= 3.5) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),3.5);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),3.5);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),3.5);
			
			return 3.5;
		}
		else if(newRate >= 3) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),3);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),3);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),3);
			
			return 3;
		}
		else if(newRate >= 2.5) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),2.5);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),2.5);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),2.5);
			
			return 2.5;
		}
		else if(newRate >= 2) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),2);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),2);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),2);
			
			return 2;
		}
		else if(newRate >= 1.5) {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),1.5);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),1.5);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),1.5);
			
			return 1.5;
		}
		else {
			if("doctor".equals(userType))
				rateRepository.updateDoctorRate(rate.getRateTo(),1);
			else if("secretary".equals(userType))
				rateRepository.updateSecretaryRate(rate.getRateTo(),1);
			else if("pharmacist".equals(userType))
				rateRepository.updatePharmacyRate(rate.getRateTo(),1);
			
			return 1;
		}
	}
}
