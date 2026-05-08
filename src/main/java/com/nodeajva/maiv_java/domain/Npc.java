package com.nodeajva.maiv_java.domain;

import com.nodeajva.maiv_java.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;

@Table(name = "npc")
@Entity
@Getter
public class Npc extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String mbtiType;

	@Enumerated(EnumType.STRING)
	@Column
	private NpcState currentState;

	// Npc 엔티티에 추가
	public static Npc create(String name, String mbtiType) {
		Npc npc = new Npc();
		npc.name = name;
		npc.mbtiType = mbtiType;
		npc.currentState = NpcState.IDLE;
		return npc;
	}

	public void startTalking() {
		if (this.currentState != NpcState.IDLE) {
			throw new RuntimeException("NPC가 대화 불가 상태입니다");
		}
	}

	public void stopTalking() {

		this.currentState = NpcState.IDLE;
	}

	public void startWaiting() {
		this.currentState = NpcState.WAITING;
	}

	public boolean isAvailable() {
		return this.currentState == NpcState.IDLE;
	}
}
