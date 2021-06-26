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

import com.healthCare.healthCareDataBase.Model.OldData;
import com.healthCare.healthCareDataBase.Repository.OldDataRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/oldData")
public class OldDataController {

	@Autowired
	OldDataRepository oldDataRepository;
	
	@PostMapping(value="add")
	public boolean add(@RequestBody OldData oldData) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		oldData.setUpdateDate(dateFormat.format(cal.getTime()));
		oldDataRepository.save(oldData);
		return true;
	}
}
