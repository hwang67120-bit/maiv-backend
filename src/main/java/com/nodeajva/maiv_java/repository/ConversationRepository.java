package com.nodeajva.maiv_java.repository;

import com.nodeajva.maiv_java.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {


    Optional<Conversation> findByNpcIdAndEndedAtIsNull(Long npcId);
}
