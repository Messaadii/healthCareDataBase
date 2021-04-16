package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.MedicamentNameAndTreatmentPeriode;
import com.healthCare.healthCareDataBase.Model.PrescriptionMedicament;
import com.healthCare.healthCareDataBase.Repository.PrescriptionMedicamentRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/prescriptionMedicament")
public class PrescriptionMedicamentController {
	
	@Autowired
	PrescriptionMedicamentRepository prescriptionMedicamentRepository;
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody final PrescriptionMedicament data) {
		prescriptionMedicamentRepository.save(data);
		return true;
	}
	
	@GetMapping(value="getMedicamentsByPrescriptionId/{id}")
	public List<MedicamentNameAndTreatmentPeriode> getMedicamentsByPrescriptionId(@PathVariable("id") final Long id) {
		return prescriptionMedicamentRepository.getMedicamentsByPrescriptionId(id);
	}

}
