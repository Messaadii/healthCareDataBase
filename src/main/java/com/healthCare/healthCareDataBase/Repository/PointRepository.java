package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Point;

public interface PointRepository extends JpaRepository<Point,Long>{
	
	@Query(value="select p.post_id from point p where p.post_id=?1 and p.user_id=?2",nativeQuery=true)
	public Long checkIfUserAlreadyAddPoint(Long postId, Long userId);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM point p WHERE p.post_id=?1 and p.user_id=?2 and p.post_type=?3",nativeQuery=true)
	public void deleteByUserIdAndQuestionId(Long postId, Long userId,String postType);
	
}
