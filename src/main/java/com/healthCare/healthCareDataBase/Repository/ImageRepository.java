package com.healthCare.healthCareDataBase.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{
	Optional<Image> findByImageName(String name);
	
	boolean existsByImageName(String imageName);

	@Modifying
    @Transactional
	@Query(value="update images i set i.image_name= ?1, i.image_type=?2, i.pic_byte=?3 where i.image_name = ?1",nativeQuery=true)
	void UpdateByImageName(String imageName, String imageType, byte[] picByte);

	long deleteByImageName(String one);
	
	
}
