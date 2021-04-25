package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Model.Comment;
import com.healthCare.healthCareDataBase.Repository.CommentRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/comment")
public class CommentController {
	
	@Autowired
	CommentRepository commentRepository;
	
	@PostMapping(value="/add")
	public Long add(@RequestBody final Comment comment) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		comment.setCommentPostDate(dateFormat.format(cal.getTime()));
		comment.setCommentPoints(0);
		commentRepository.save(comment);
		return comment.getCommentId();
	}
	
	@PostMapping(value="/getAll")
	public List<Comment> getAll(@RequestBody final PageableAndIdDto data){
		Pageable pageable=PageRequest.of(data.getPage(), data.getSize(), Sort.by("comment_points").descending());
		return commentRepository.getAll(data.getId(),pageable);
	}

}
