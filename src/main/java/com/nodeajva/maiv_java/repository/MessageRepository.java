package com.nodeajva.maiv_java.repository;

import com.nodeajva.maiv_java.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, Long> {

    List<Message> findByConversationId(Long conversationId);


}
