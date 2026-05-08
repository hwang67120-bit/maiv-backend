package com.nodeajva.maiv_java.dto.response;

import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.domain.NpcState;

public record NpcResponse(

	Long id,
	String name,
	String mbtiType,
	NpcState currentState) {

	public static NpcResponse from(Npc npc) {
		return new NpcResponse(
			npc.getId(),
			npc.getName(),
			npc.getMbtiType(),
			npc.getCurrentState()
		);
	}

}
