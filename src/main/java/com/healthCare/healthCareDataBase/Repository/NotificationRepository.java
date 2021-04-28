package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long>{

	@Query(value="select * from notification n where n.recipient_id=?1",nativeQuery=true)
	List<Notification> getAllById(Long id, Pageable pageable);

	@Modifying
    @Transactional
	@Query(value="update notification n set n.is_unread=?2 where n.notification_id = ?1",nativeQuery=true)
	void changeUnreadNotification(Long id, boolean unread);

}
