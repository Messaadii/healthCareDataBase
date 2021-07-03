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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.PageableDto;
import com.healthCare.healthCareDataBase.Model.Question;
import com.healthCare.healthCareDataBase.Repository.QuestionRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/question")
public class QuestionContoller {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping(value="/add")
	public Long add (@RequestBody final Question data) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		data.setQuestionPostTime(dateFormat.format(cal.getTime()));
		data.setQuestionPoints(0);
		questionRepository.save(data);
		return data.getQuestionId();
	}
	
	@PostMapping(value="/getQuestionsByType")
	public List<Question> getQuestionsByType (@RequestBody final PageableDto data){
		Pageable pageable=PageRequest.of(data.getPage(), data.getSize(), Sort.by("question_points").descending());
		return questionRepository.getQuestionsByType(data.getType(),data.getWords(),pageable);
	}
	
	@PostMapping(value="/getQuestionsByUserId")
	public List<Question> getQuestionsByUserId(@RequestBody final PageableAndIdDto data){
		Pageable pageable=PageRequest.of(data.getPage(), data.getSize(), Sort.by("question_points").descending());
		return questionRepository.getQuestionsByUserId(data.getId(),pageable);
	}
	
	@GetMapping(value="/getQuestionsTypes")
	public List<String> getQuestionsTypes(){
		return questionRepository.getQuestionsTypes();
	}

}
