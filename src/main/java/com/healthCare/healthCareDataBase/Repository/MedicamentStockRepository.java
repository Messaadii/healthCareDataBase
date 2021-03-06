package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.MedicamentStock;

public interface MedicamentStockRepository extends JpaRepository <MedicamentStock,Integer>{

	@Modifying
	@Transactional
	@Query(value="INSERT INTO medicament_stocks (medicament_id,medicament_stock_qte, pharmacy_id,medicament_stock_add_date) VALUES (?1,?2,?3,?4)",nativeQuery=true)
	void addMedicamentStock(Integer medicamentId, Integer medicamentStockQte, Integer pharmacyId, String format);

	@Query(value="select m.medicament_id from medicament_stocks m where m.medicament_id=?1 and m.pharmacy_id=?2",nativeQuery=true)
	Integer checkIfStockExist(Integer medicamentId, Integer pharmacyId);
	
	@Query(value="select m.medicament_stock_qte from medicament_stocks m where m.medicament_id=?1 and m.pharmacy_id=?2",nativeQuery=true)
	Integer getStockQte(Integer medicamentId, Integer pharmacyId);
	
	@Modifying
	@Transactional
	@Query(value="update medicament_stocks ms set ms.medicament_stock_qte=?2 where ms.medicament_id=?1 and ms.pharmacy_id=?3",nativeQuery=true)
	void updateMedicamentStock(Integer medicamentId, Integer medicamentStockQte, Integer pharmacyId);

}
