package com.nodeajva.maiv_java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nodeajva.maiv_java.domain.Conversation;
import com.nodeajva.maiv_java.domain.Message;
import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.domain.SenderType;
import com.nodeajva.maiv_java.repository.ConversationRepository;
import com.nodeajva.maiv_java.repository.MessageRepository;
import com.nodeajva.maiv_java.repository.NpcRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationService {

	private final ConversationRepository conversationRepository;
	private final MessageRepository messageRepository;
	private final NpcRepository npcRepository;
	private final OllamaService ollamaService;

	public Conversation startConversation(Long npcId, String playerName) {

		Npc npc = npcRepository.findById(npcId)
			.orElseThrow(() -> new RuntimeException("NPC를 찾을 수 없습니다"));

		// 도메인 메소드로 상태 변경 + 검증
		npc.startTalking();

		// 팩토리 메소드로 생성
		Conversation conversation = Conversation.create(npc, playerName);

		conversationRepository.save(conversation);
		npcRepository.save(npc);

		return conversation;
	}

	public String sendMessage(Long conversationId, String playerMessage) {

		Conversation conversation = conversationRepository.findById(conversationId)
			.orElseThrow(() -> new RuntimeException("대화를 찾을 수 없습니다"));

		if (conversation.getEndedAt() != null) {
			throw new RuntimeException("이미 종료된 대화입니다");
		}

		// 팩토리 메소드로 플레이어 메시지 생성
		Message playerMsg = Message.create(

			conversation.getId().toString(),

			SenderType.PLAYER, playerMessage
		);

		messageRepository.save(playerMsg);

		// Ollama 호출
		List<Message> history = messageRepository.findByConversationId(conversationId);
		String aiResponse = ollamaService.generateResponse(conversation.getNpc(), history);

		// NPC 응답 저장
		Message npcMsg = Message.create(

			conversation.getId().toString(),

			SenderType.NPC, aiResponse
		);

		messageRepository.save(npcMsg);

		return aiResponse;
	}

	public Conversation findOrCreateConversation(Long npcId, String playerName) {
		return conversationRepository
			.findByNpcIdAndEndedAtIsNull(npcId)
			.orElseGet(() -> startConversation(npcId, playerName));
	}

	public void endConversation(Long conversationId) {

		Conversation conversation = conversationRepository.findById(conversationId)
			.orElseThrow(() -> new RuntimeException("대화를 찾을 수 없습니다"));

		if (conversation.getEndedAt() != null) {
			throw new RuntimeException("이미 종료된 대화입니다");
		}

		// 도메인 메소드로 종료
		conversation.end();

		conversationRepository.save(conversation);
		npcRepository.save(conversation.getNpc());
	}
}