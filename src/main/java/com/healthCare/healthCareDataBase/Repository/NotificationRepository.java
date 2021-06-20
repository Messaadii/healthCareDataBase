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
			+ " if(n.sender_id != -1,"
			+ " case"
			+ " when u.user_type = 'doctor' then concat('Dr. ',d.doctor_first_name,' ',d.doctor_last_name)"
			+ " when u.user_type = 'pharmacist' then concat('Ph. ',ph.pharmacy_full_name)"
			+ " when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name)"
			+ " when u.user_type = 'secretary' then concat(s.secretary_first_name,' ',s.secretary_last_name)"
			+ " end,'') as name"
			+ " from notification n, users u ,patients p,pharmacies ph, doctors d, secretaries s"
			+ " where n.recipient_id=?1 and if(n.sender_id != -1,n.sender_id=u.user_id,n.recipient_id=?1) and"
			+ " if(n.sender_id != -1,"
			+ " case"
			+ " when u.user_type = 'doctor' then d.user_id=u.user_id"
			+ " when u.user_type = 'pharmacist' then ph.user_id=u.user_id"
			+ " when u.user_type = 'patient' then p.user_id=u.user_id"
			+ " when u.user_type = 'secretary' then s.user_id=u.user_id"
			+ " end"
			+ " ,n.recipient_id=?1)"
			+ " and n.notification_type != 'userSelectYouForPres'"
			+ " and n.notification_type != 'changeAppDateReq'"
			+ " group by n.notification_id",nativeQuery=true)
	List<NotificationGetDto> getAllById(Long id, Pageable pageable);

	@Modifying
    @Transactional
	@Query(value="update notification n set n.is_unread=?2 where n.notification_id = ?1",nativeQuery=true)
	void changeUnreadNotification(Long id, boolean unread);

	@Query(value="select if(count(n.sender_id) = 1, n.recipient_id, 0)"
			+ " from notification n"
			+ " where n.sender_id = ?1"
			+ " and n.notification_parameter = ?2"
			+ " and n.notification_type = ?3",nativeQuery=true)
	long checkIfNotificationExistByIdsAndStatus(long senderId, String notificationParameter, String notificationType);

	@Modifying
    @Transactional
	@Query(value="update notification n"
			+ " set n.recipient_id=?2"
			+ " where n.sender_id = ?1"
			+ " and n.notification_parameter = ?3"
			+ " and n.notification_type = ?4",nativeQuery=true)
	void updateNotificationBySenderIdParameterAndType(long senderId, long recipientId,
			String notificationParameter, String notificationType);

	@Modifying
    @Transactional
	@Query(value="insert into notification"
			+ " (notification_id, notification_parameter,is_unread, notification_type, recipient_id, sender_id, time_sent)"
			+ " values (?1,?2,?3,?4,?5,?6,?7)",nativeQuery=true)
	void saveNotification(Long notificationId, String notificationParameter, boolean isUnread, String notificationType,
			long recipientId, long senderId, String timeSent);

	@Modifying
    @Transactional
	@Query(value="delete from notification n where n.notification_parameter = ?1 and n.notification_type = ?2",nativeQuery=true)
	void deleteNotificationByPamareterAndType(String stringOne, String stringTwo);

	@Modifying
    @Transactional
	@Query(value="delete from notification n"
			+ " where n.notification_id = ?1 and"
			+ " n.recipient_id = (select u.user_id from users u"
			+ " where u.user_secure_login = ?2)",nativeQuery=true)
	int deleteNotificationById(long id, String secureLogin);

}
