package com.healthCare.healthCareDataBase.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Validation;
import com.healthCare.healthCareDataBase.Repository.ValidationRepository;

@RestController
@CrossOrigin
@RequestMapping(value="/validation")
public class ValidationResource {
	
	@Autowired
	ValidationRepository validationRepository;
	
	@GetMapping(value="/all")
	public List<Validation>getAll(){
		return validationRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Validation validation) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		validation.setValidationDate(dateFormat.format(cal.getTime()));
		validationRepository.save(validation);
		return"validationAdd";
	}
}
