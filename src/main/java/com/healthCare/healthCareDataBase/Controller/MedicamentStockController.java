package com.healthCare.healthCareDataBase.Controller;

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

import com.healthCare.healthCareDataBase.Dtos.MedicamentStockDto;
import com.healthCare.healthCareDataBase.Dtos.SearchMedForPharmacyDto;
import com.healthCare.healthCareDataBase.Model.MedicamentStock;
import com.healthCare.healthCareDataBase.Repository.MedicamentStockRepository;
import com.healthCare.healthCareDataBase.Repository.PharmacyRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/medicamentstock")
public class MedicamentStockController {
	
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
				Long newQte=medicamentStockRepository.getStockQte(medicamentStockModel.getMedicamentId(),medicamentStockModel.getPharmacyId()) +medicamentStockModel.getMedicamentStockQte();
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
	
	@GetMapping(value="/getStockNumberByPharmacyId/{id}")
	public Integer getStockNumberByPharmacyId(@PathVariable final Long id) {
		return medicamentStockRepository.getStockNumberByPharmacyId(id);
	}
	
	@Transactional
	@DeleteMapping(value="/deleteByPharmacyId/{id}")
	public boolean deleteByPharmacyId(@PathVariable final Long id) {
		medicamentStockRepository.deleteByPharmacyId(id);
		return true;
	}
	
	@Transactional
	@DeleteMapping(value="/deleteByMedicamentStockId/{id}")
	public boolean deleteByMedicamentStockId(@PathVariable final Long id) {
		medicamentStockRepository.deleteByMedicamentStockId(id);
		return true;
	}
	
	@PostMapping(value="searchMedByNameAndPharmacyId")
	public List<MedicamentStock> searchMedByNameAndPharmacyId(@RequestBody final SearchMedForPharmacyDto data) {
		return medicamentStockRepository.searchMedByNameAndPharmacyId(data.getPharmacyId(),data.getMedicamentName());
	}
}
