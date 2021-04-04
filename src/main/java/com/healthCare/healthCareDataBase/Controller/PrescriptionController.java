package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Prescription;
import com.healthCare.healthCareDataBase.Repository.PrescriptionRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/prescription")
public class PrescriptionController {

	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	@GetMapping(value="/all")
	public List<Prescription>getAll(){
		return prescriptionRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public boolean add(@RequestBody final Prescription prescription) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		prescription.setPrescriptionDate(dateFormat.format(cal.getTime()));
		prescriptionRepository.save(prescription);
		return true;
	}
	
	@PostMapping(value="/{prescriptionId}/addMedicamnet/{medicamentId}")
	public String addMedicamnet(@PathVariable(name="prescriptionId") Integer prescriptionId,@PathVariable(name="medicamentId") Integer medicamentId ) {
		if(prescriptionRepository.checkIfMedicamentAlreadyAdded(prescriptionId,medicamentId)==prescriptionId)
			return "medicament already added";
		else {
			prescriptionRepository.addMedicamentToPrescription(prescriptionId,medicamentId);
			return "medicament add";
		}
	}
}
