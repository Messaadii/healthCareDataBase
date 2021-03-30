package com.healthCare.healthCareDataBase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.IntegerAndString;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Model.Appointment;
import com.healthCare.healthCareDataBase.Repository.AppointmentRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/appointment")
public class AppointmentController {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@PostMapping(value="/add") 
	public boolean add(@RequestBody final Appointment appointment) {
		if(appointmentRepository.checkIfAppointmentAlreadyTaken(appointment.getDoctorId(), appointment.getPatientId()) > 0)
			return false;
		else {
			appointment.setAppointmentStatus("pending");
			appointmentRepository.save(appointment);
			return true;
		}
	}
	
	@PostMapping(value="/appointmentsCountByDoctorIdAndDate")
	public Integer appointmentsCountByDoctorIdAndDate(@RequestBody final IntegerAndString integerAndString) {
		return appointmentRepository.appointmentsCountByDoctorIdAndDate(integerAndString.getInteger(),integerAndString.getString());
	}
	
	@DeleteMapping(value="/deleteAppointmentById/{id}")
	public boolean deleteAppointmentById(@PathVariable("id") Long id) {
		appointmentRepository.deleteById(id);
		return true;
	}
	
	@PostMapping(value="updateAppointmentDateById")
	public boolean updateAppointmentDateById(@RequestBody final IntegerAndString integerAndString ) {
		appointmentRepository.updateAppointmentDateById(integerAndString.getInteger(), integerAndString.getString());
		return true;
	}
	
	@PostMapping(value="getPatientAppointmentByPatientId")
	public List<Appointment> getPatientAppointmentByPatientId(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		Pageable pageable= PageRequest.of(pageableAndIdDto.getPage(), pageableAndIdDto.getSize(), Sort.by("appointment_date").ascending());
		return appointmentRepository.getPatientAppointmentByPatientId(pageableAndIdDto.getId(), pageable);
	}
	
	@PostMapping(value="getAppointmentByDoctorIdAndDate")
	public List<Appointment> getAppointmentByDoctorIdAndDate(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		Pageable pageable= PageRequest.of(pageableAndIdDto.getPage(), pageableAndIdDto.getSize(), Sort.by("appointment_date").ascending());
		return appointmentRepository.getAppointmentByDoctorIdAndDate(pageableAndIdDto.getId(),pageableAndIdDto.getDate(), pageable);
	}
	@PostMapping(value="getAppointmentNumberByDoctorIdAndDate")
	public Integer getAppointmentNumberByDoctorIdAndDate(@RequestBody final PageableAndIdDto pageableAndIdDto) {
		return appointmentRepository.getAppointmentNumberByDoctorIdAndDate(pageableAndIdDto.getId(),pageableAndIdDto.getDate());
	}
	
}