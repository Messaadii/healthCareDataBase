package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament,Long> {

	@Query(value="select m.medicament_name from medicaments m where m.medicament_name like ?1%",nativeQuery=true)
	List<String> getMedicamentsByFirstLetters(String letters);

}
