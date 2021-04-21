package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.MedicamentStock;

public interface MedicamentStockRepository extends JpaRepository <MedicamentStock,Long>{

	long deleteByPharmacyId(Long id);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO medicament_stocks (medicament_id,medicament_stock_qte, pharmacy_id,medicament_stock_add_date) VALUES (?1,?2,?3,?4)",nativeQuery=true)
	void addMedicamentStock(Long medicamentId, Long medicamentStockQte, Long pharmacyId, String format);

	@Query(value="select m.medicament_id from medicament_stocks m where m.medicament_id=?1 and m.pharmacy_id=?2",nativeQuery=true)
	Long checkIfStockExist(Long medicamentId, Long pharmacyId);
	
	@Query(value="select m.medicament_stock_qte from medicament_stocks m where m.medicament_id=?1 and m.pharmacy_id=?2",nativeQuery=true)
	Long getStockQte(Long medicamentId, Long pharmacyId);
	
	@Modifying
	@Transactional
	@Query(value="update medicament_stocks ms set ms.medicament_stock_qte=?2 where ms.medicament_id=?1 and ms.pharmacy_id=?3",nativeQuery=true)
	void updateMedicamentStock(Long medicamentId, Long medicamentStockQte, Long pharmacyId);

	@Query(value = "SELECT count(md.pharmacy_id) FROM medicament_stocks md where md.pharmacy_id=?1",nativeQuery=true)
	Integer getStockNumberByPharmacyId(Long id);

	@Query(value = "SELECT * FROM medicament_stocks md where md.pharmacy_id=?1 and md.medicament_name like ?2",nativeQuery=true)
	List<MedicamentStock> searchMedByNameAndPharmacyId(Long pharmacyId, String medicamentName);

}
