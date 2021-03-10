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

import com.healthCare.healthCareDataBase.Model.MedicamentStock;
import com.healthCare.healthCareDataBase.Repository.MedicamentStockRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

import dtos.MedicamentStockDto;

@CrossOrigin
@RestController
@RequestMapping(value="/medicamentstock")
public class MedicamentStockResource {
	
	@Autowired
	MedicamentStockRepository medicamentStockRepository;
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@GetMapping(value="/all")
	public List<MedicamentStock>getAll(){
		return medicamentStockRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final MedicamentStockDto medicamentStockModel) {
		if(pharmacyRepository.existsById(medicamentStockModel.getPharmacyId())) {
			if(medicamentStockRepository.checkIfStockExist(medicamentStockModel.getMedicamentId(),medicamentStockModel.getPharmacyId())==medicamentStockModel.getMedicamentId()) {
				Integer newQte=medicamentStockRepository.getStockQte(medicamentStockModel.getMedicamentId(),medicamentStockModel.getPharmacyId()) +medicamentStockModel.getMedicamentStockQte();
				medicamentStockRepository.updateMedicamentStock(medicamentStockModel.getMedicamentId(),newQte,medicamentStockModel.getPharmacyId());
				return "stock already exist, qte updated to " + newQte;
			}else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				medicamentStockRepository.addMedicamentStock(medicamentStockModel.getMedicamentId(),medicamentStockModel.getMedicamentStockQte(),medicamentStockModel.getPharmacyId(), dateFormat.format(cal.getTime()));
				return "stock created with qte: "+medicamentStockModel.getMedicamentStockQte();
			}
		}else
			return "there is no pharmacy with id: "+medicamentStockModel.getPharmacyId();
		
	}
}
