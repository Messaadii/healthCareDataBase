package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.ConversationsGetDto;
import com.healthCare.healthCareDataBase.Model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation,Long>{

	@Query(value="select distinct c.conversation_id,"
			+ " c.conversation_status,"
			+ " c.open_date,"
			+ " c.last_update_date,"
			+ " m.message_content,"
			+ " if(c.opened_by=?1, c.opened_to,c.opened_by) as recipient,"
			+ " u.user_type,"
			+ " case"
			+ "		  when u.user_type = 'doctor' then d.doctor_first_name"
			+ "		  when u.user_type = 'patient' then p.patient_first_name"
			+ "		  when u.user_type = 'pharmacist' then ph.pharmacy_full_name"
			+ "	end as first_name ,"
			+ " case"
			+ "		  when u.user_type = 'doctor' then d.doctor_last_name"
			+ "		  when u.user_type = 'patient' then p.patient_last_name"
		    + " end as last_name"
			+ "	from conversation c, users u, patients p, doctors d, pharmacies ph, message m"
			+ "	where u.user_id=if(c.opened_by=?1, c.opened_to,c.opened_by) and"
			+ " (c.opened_by=?1 or c.opened_to=?1) and"
			+ " m.message_date=c.last_update_date"
			+ " and ((m.sender_id=c.opened_by and m.recipient_id=c.opened_to) or (m.sender_id=c.opened_to and m.recipient_id=c.opened_by))"
			+ " and case "
			+ "		  when u.user_type = 'doctor' then d.user_id=u.user_id"
		    + "		  when u.user_type = 'patient' then p.user_id=u.user_id"
			+ "		  when u.user_type = 'pharmacist' then ph.user_id=u.user_id"
			+ "		end",nativeQuery=true)
	List<ConversationsGetDto> getConversationByUserId(Long userId, Pageable pageable);

	@Transactional
	@Modifying
	@Query(value="update conversation c set c.conversation_status=?2 where c.conversation_id=?1",nativeQuery=true)
	void updateConversationStatusById(Long id, String status);

}
