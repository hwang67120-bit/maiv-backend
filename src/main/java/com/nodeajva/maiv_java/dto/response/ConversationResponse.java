package com.nodeajva.maiv_java.dto.response;

import com.nodeajva.maiv_java.domain.Conversation;

public record ConversationResponse(

	Long id,
	String npcName,
	String playerName
) {

	public static ConversationResponse from(Conversation conversation) {
		return new ConversationResponse(
			conversation.getId(),
			conversation.getNpc().getName(),
			conversation.getPlayerName()
		);

	}
}
