package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Message;

public interface MessageRepository extends JpaRepository<Message,Long>{

	@Query(value="select * from message m where m.conversation_id=?1",nativeQuery=true)
	List<Message> getMessagesByConversationId(Long id, Pageable pageable);

}
