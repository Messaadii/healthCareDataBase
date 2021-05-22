package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.NotificationGetDto;
import com.healthCare.healthCareDataBase.Model.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long>{

	@Query(value="select n.notification_id,"
			+ " if(n.is_unread=true,true,false) as is_unread,"
			+ " n.notification_parameter,"
			+ " n.notification_type,"
			+ " n.recipient_id,"
			+ " n.sender_id,"
			+ " n.time_sent,"
			+ " if(n.sender_id != 0,"
			+ " case"
			+ " when u.user_type = 'doctor' then concat('Dr. ',d.doctor_first_name,' ',d.doctor_last_name)"
			+ " when u.user_type = 'pharmacist' then concat('Ph. ',ph.pharmacy_full_name)"
			+ " when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name)"
			+ " end,'') as name"
			+ " from notification n, users u ,patients p,pharmacies ph, doctors d"
			+ " where n.recipient_id=?1 and if(n.sender_id != 0,n.sender_id=u.user_id,n.recipient_id=?1) and"
			+ " case"
			+ " when u.user_type = 'doctor' then d.user_id=u.user_id"
			+ " when u.user_type = 'pharmacist' then ph.user_id=u.user_id"
			+ " when u.user_type = 'patient' then p.user_id=u.user_id"
			+ " end group by n.notification_id",nativeQuery=true)
	List<NotificationGetDto> getAllById(Long id, Pageable pageable);

	@Modifying
    @Transactional
	@Query(value="update notification n set n.is_unread=?2 where n.notification_id = ?1",nativeQuery=true)
	void changeUnreadNotification(Long id, boolean unread);

}
