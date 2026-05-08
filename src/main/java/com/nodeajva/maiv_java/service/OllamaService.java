package com.nodeajva.maiv_java.service;

import com.nodeajva.maiv_java.domain.Message;
import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.domain.SenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaService {

	private final WebClient webClient;

	@Value("${ollama.url}")
	private String ollamaUrl;

	public String generateResponse(Npc npc, List<Message> history) {

		String systemPrompt = "당신은 " + npc.getName() + "입니다. "
			+ mbtiToDescription(npc.getMbtiType())
			+ " 당신은 중세 마을의 주민입니다."
			+ " 반드시 한국어로 한두 문장으로만 짧게 대답하세요."
			+ " 마크다운, 목록, 기호를 절대 사용하지 마세요."
			+ " 중세 시대 배경에 맞는 대화만 하세요.";

		List<Map<String, String>> messages = new ArrayList<>();

		Map<String, String> systemMessage = new HashMap<>();
		systemMessage.put("role", "system");
		systemMessage.put("content", systemPrompt);
		messages.add(systemMessage);

		for (Message msg : history) {
			Map<String, String> map = new HashMap<>();
			map.put("role", msg.getSenderType() == SenderType.PLAYER ? "user" : "assistant");
			map.put("content", msg.getContent());
			messages.add(map);
		}

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", "exaone3.5:2.4b");
		requestBody.put("messages", messages);
		requestBody.put("stream", false);

		try {
			String response = webClient.post()
				.uri(ollamaUrl)
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(String.class)
				.block();

			log.info("Ollama 응답: {}", response);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(response);
			String content = root.path("message").path("content").asText();
			log.info("파싱 결과: {}", content);
			return content;

		} catch (Exception e) {
			log.error("Ollama 오류: {}", e.getMessage());
			return "응답 처리 중 오류가 발생했습니다";
		}
	}

	private String mbtiToDescription(String mbtiTyp) {
		return switch (mbtiTyp) {
			case "INTJ" -> "전략적이고 독립적인 완벽주의자. 논리적으로 말하며 감정표현이 적다.";
			case "INTP" -> "논리적이고 분석적인 사색가. 아이디어를 탐구하길 좋아한다.";
			case "ENTJ" -> "강한 리더십과 추진력을 가진 지도자. 효율성을 중시한다.";
			case "ENTP" -> "창의적이고 논쟁을 즐기는 발명가. 새로운 아이디어를 좋아한다.";
			case "INFJ" -> "통찰력 있고 이상주의적인 조언자. 깊은 대화를 선호한다.";
			case "INFP" -> "감성적이고 창의적인 몽상가. 자신의 가치관을 중시한다.";
			case "ENFJ" -> "따뜻하고 카리스마 있는 선도자. 타인을 돕는 것을 좋아한다.";
			case "ENFP" -> "열정적이고 창의적인 활동가. 감정을 잘 드러내고 사교적이다.";
			case "ISTJ" -> "신중하고 책임감 강한 현실주의자. 사실에 근거해 말한다.";
			case "ISFJ" -> "따뜻하고 헌신적인 수호자. 타인을 배려하고 전통을 중시한다.";
			case "ESTJ" -> "체계적이고 현실적인 관리자. 규칙과 질서를 중요시한다.";
			case "ESFJ" -> "친절하고 사교적인 돌봄이. 타인의 감정에 민감하다.";
			case "ISTP" -> "과묵하고 분석적인 장인. 실용적인 해결책을 찾는다.";
			case "ISFP" -> "온화하고 감성적인 예술가. 자유롭고 개방적이다.";
			case "ESTP" -> "활동적이고 현실적인 사업가. 즉흥적이고 대담하다.";
			case "ESFP" -> "자유롭고 활발한 엔터테이너. 주변을 즐겁게 만든다.";
			default -> "친절하고 대화를 좋아하는 성격이다.";
		};
	}
}