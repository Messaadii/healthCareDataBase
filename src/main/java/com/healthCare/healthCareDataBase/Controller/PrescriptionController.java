package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.GetPatientPrescription;
import com.healthCare.healthCareDataBase.Dtos.IdAndCodeDto;
import com.healthCare.healthCareDataBase.Dtos.IdPatientIdAndDoctorIdDto;
import com.healthCare.healthCareDataBase.Dtos.PrescriptionByIdAndStatus;
import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Model.Prescription;
import com.healthCare.healthCareDataBase.Repository.PrescriptionRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/prescription")
public class PrescriptionController {

	@Autowired
	PrescriptionRepository prescriptionRepository;
	@Autowired
	NotificationController notificationController;
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value="/all")
	public List<Prescription>getAll(){
		return prescriptionRepository.findAll();
	}
	
	@PostMapping(value="/add") 
	public Long add(@RequestBody final Prescription prescription) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		prescription.setPrescriptionDate(dateFormat.format(cal.getTime()));
		prescription.setPrescriptionStatus("pending");
		prescription.setPrescriptionCode((int)Math.floor(Math.random()*(9999-1000+1)+1000));
		prescriptionRepository.save(prescription);
		
		Notification notification = new Notification();
		notification.setNotificationType("doctorAddPrescription");
		notification.setSenderId(prescription.getDoctorId());
		notification.setNotificationParameter(prescription.getPrescriptionId()+"");
		notification.setRecipientId(prescription.getPatientId());
		notificationController.add(notification);
		
		WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
		webSocketNot.setType("notification");
		webSocketNot.setData(userRepository.getUsernameByUserid(prescription.getDoctorId()));
		webSocketNot.setNotification(notification);
		template.convertAndSend("/topic/notification/"+prescription.getPatientId(),webSocketNot);
		
		return prescription.getPrescriptionId();
	}
	
	@PostMapping(value="/{prescriptionId}/addMedicamnet/{medicamentId}")
	public String addMedicamnet(@PathVariable(name="prescriptionId") Integer prescriptionId,@PathVariable(name="medicamentId") Integer medicamentId ) {
		if(prescriptionRepository.checkIfMedicamentAlreadyAdded(prescriptionId,medicamentId)==prescriptionId)
			return "medicament already added";
		else {
			prescriptionRepository.addMedicamentToPrescription(prescriptionId,medicamentId);
			return "medicament add";
		}
	}
	
	@PostMapping(value="/deleteById")
	public boolean deleteById(@RequestBody final IdPatientIdAndDoctorIdDto requestData) {
		
		TwoStrings data = new TwoStrings();
		data.setStringOne(requestData.getId()+"");
		data.setStringTwo("doctorAddPrescription");
		notificationController.deleteNotificationByPamareterAndType(data);
		
		WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
		webSocketNot.setType("notification");
		webSocketNot.setData(userRepository.getUsernameByUserid(requestData.getDoctorId()));
		
		Notification notification = new Notification();
		notification.setNotificationType("doctorDeletePrescription");
		notification.setNotificationParameter(requestData.getId()+"");
		webSocketNot.setNotification(notification);
		
		template.convertAndSend("/topic/notification/"+requestData.getPatientId(),webSocketNot);
		
		prescriptionRepository.deleteById(requestData.getId());
		return true;
	}
	
	@PostMapping(value="/getPrescriptionByDoctorIdPatientIdAndDate")
	public Prescription getPrescriptionByDoctorIdPatientIdAndDate(@RequestBody final Prescription prescription) {
		return prescriptionRepository.getPrescriptionByDoctorIdPatientIdAndDate(prescription.getDoctorId(),prescription.getPatientId(),prescription.getPrescriptionDate());
	}
	
	@PostMapping(value="/confirmPrescriptionById")
	public boolean confirmPrescriptionById(@RequestBody final IdAndCodeDto data) {
		int numberOfUpdatedRow = prescriptionRepository.confirmPrescriptionById(data.getId(),data.getCode());
		
		if(numberOfUpdatedRow==1) {
			WebSocketNotificationDto webSocketNot = new WebSocketNotificationDto();
			webSocketNot.setType("confirmPrescription");
			webSocketNot.setData(data.getId()+"");
			template.convertAndSend("/topic/notification/"+data.getPatientId(),webSocketNot);
			return true;
		}
		else
			return false;
	}
	
	@PostMapping(value="/getPrescriptionsByPatientIdAndPrescriptionStatus")
	public List<GetPatientPrescription> getPrescriptionsByPatientIdAndPrescriptionStatus(@RequestBody final PrescriptionByIdAndStatus data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("prescription_id").descending());
		
		return prescriptionRepository.getPrescriptionsByPatientIdAndPrescriptionStatus(data.getPatientId(),data.getPrescriptionStatus(),pageable);
	}
	
}
