package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	@Query(value="select * from comment c where c.post_id=?1",nativeQuery=true)
	List<Comment> getAll(Long id, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value="update comment c set c.comment_points = (c.comment_points+1) where c.comment_id=?1",nativeQuery=true)
	void incrementCommentPointById(Long questionId);

	@Modifying
	@Transactional
	@Query(value="update comment c set c.comment_points = (c.comment_points-1) where c.comment_id=?1",nativeQuery=true)
	void decrementQuestionPointById(Long postId);

	@Query(value="select q.post_by from question q where q.question_id=?1",nativeQuery=true)
	long getPosterIdByPostId(Long postId);

}
