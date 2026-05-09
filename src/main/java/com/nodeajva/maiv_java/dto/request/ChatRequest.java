package com.nodeajva.maiv_java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatRequest(

	@JsonProperty("npc_id")
	Long npcId,
	String message) {

}
