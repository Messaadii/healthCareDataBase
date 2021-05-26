package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

	@Query(value="select * from question",nativeQuery=true)
	List<Question> getAll(Pageable pageable);

	@Modifying
	@Transactional
	@Query(value="update question q set q.question_points = (q.question_points+1) where q.question_id=?1",nativeQuery=true)
	void incrementQuestionPointById(Long questionId);
	
	@Modifying
	@Transactional
	@Query(value="update question q set q.question_points = (q.question_points-1) where q.question_id=?1",nativeQuery=true)
	void decrementQuestionPointById(Long questionId);

	@Query(value="select * from question q where q.post_by=?1",nativeQuery=true)
	List<Question> getQuestionsByUserId(Long id, Pageable pageable);

}
