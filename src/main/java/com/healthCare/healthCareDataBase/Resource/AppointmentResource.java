package com.healthCare.healthCareDataBase.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Model.Appointment;
import com.healthCare.healthCareDataBase.Repository.AppointmentRepository;

import dtos.IntegerAndString;

@CrossOrigin
@RestController
@RequestMapping(value="/appointment")
public class AppointmentResource {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@PostMapping(value="/add") 
	public String add(@RequestBody final Appointment appointment) {
		if(appointmentRepository.checkIfAppointmentAlreadyTaken(appointment.getDoctorId(), appointment.getPatientId()) > 0)
			return "appointemntAlreadyTaken";
		else {
			appointment.setAppointmentStatus("pending");
			appointmentRepository.save(appointment);
			return "appointemntAdded";
		}
	}
	
	@PostMapping(value="/appointmentsCountByDoctorIdAndDate")
	public Integer appointmentsCountByDoctorIdAndDate(@RequestBody final IntegerAndString integerAndString) {
		return appointmentRepository.appointmentsCountByDoctorIdAndDate(integerAndString.getInteger(),integerAndString.getString());
	}
	
	@DeleteMapping(value="/deleteAppointmentById/{id}")
	public String deleteAppointmentById(@PathVariable("id") Integer id) {
		appointmentRepository.deleteById(id);
		return "deleted";
	}
	
	@PostMapping(value="updateAppointmentDateById")
	public String updateAppointmentDateById(@RequestBody final IntegerAndString integerAndString ) {
		appointmentRepository.updateAppointmentDateById(integerAndString.getInteger(), integerAndString.getString());
		return "updated";
	}
	
}
