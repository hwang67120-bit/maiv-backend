package com.nodeajva.maiv_java.domain;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;

@Document(collection = "messages")  // @Entity 대신
@Getter
public class Message {

	@Id
	private String id;

	private String conversationId;
	private SenderType senderType;
	private String content;
	private LocalDateTime sentAt;

	public static Message create(String conversationId, SenderType senderType, String content) {
		Message message = new Message();
		message.conversationId = conversationId;
		message.senderType = senderType;
		message.content = content;
		message.sentAt = LocalDateTime.now();
		return message;
	}
}
