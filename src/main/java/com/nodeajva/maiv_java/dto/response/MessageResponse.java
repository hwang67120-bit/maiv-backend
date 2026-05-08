package com.nodeajva.maiv_java.dto.response;

import java.time.LocalDateTime;

import com.nodeajva.maiv_java.domain.Message;
import com.nodeajva.maiv_java.domain.SenderType;

public record MessageResponse(

	String id,
	String conversationId,
	SenderType senderType,
	String content,
	LocalDateTime sentAt
) {

	public static MessageResponse from(Message message) {
		return new MessageResponse(
			message.getId(),
			message.getConversationId(),
			message.getSenderType(),
			message.getContent(),
			message.getSentAt()
		);
	}
}
