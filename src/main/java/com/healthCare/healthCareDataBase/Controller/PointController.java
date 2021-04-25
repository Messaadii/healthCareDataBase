package com.healthCare.healthCareDataBase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Point;
import com.healthCare.healthCareDataBase.Repository.CommentRepository;
import com.healthCare.healthCareDataBase.Repository.PointRepository;
import com.healthCare.healthCareDataBase.Repository.QuestionRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/point")
public class PointController {
	
	@Autowired
	PointRepository pointRepository;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	CommentRepository commentRepository;
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody final Point point) {
		if(pointRepository.checkIfUserAlreadyAddPoint(point.getPostId(),point.getUserId()) == point.getPostId())
			return false;
		else {
			pointRepository.save(point);
			if("question".equals(point.getPostType()))
			    questionRepository.incrementQuestionPointById(point.getPostId());
			else if("comment".equals(point.getPostType()))
				commentRepository.incrementCommentPointById(point.getPostId());
			return true;
		}
		
	}
	
	@DeleteMapping(value="/deleteById/{postId}/{userId}/{postType}")
	public boolean deleteById(@PathVariable("postId") final Long postId,@PathVariable("userId") final Long userId,@PathVariable("postType") final String postType) {
		if(pointRepository.checkIfUserAlreadyAddPoint(postId,userId) == postId) {
			pointRepository.deleteByUserIdAndQuestionId(postId,userId,postType);
			if("question".equals(postType))
			    questionRepository.incrementQuestionPointById(postId);
			else if("comment".equals(postType))
				commentRepository.decrementQuestionPointById(postId);
			questionRepository.decrementQuestionPointById(postId);
			return true;
		}else
			return false;
		
	}

}
