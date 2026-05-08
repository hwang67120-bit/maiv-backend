package com.nodeajva.maiv_java.domain;

import java.time.LocalDateTime;

import com.nodeajva.maiv_java.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "conversation")
@Entity
@Getter
public class Conversation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "npc_id")
	private Npc npc;

	@Column
	private String playerName;

	@Column
	private LocalDateTime endedAt ;


	// Conversation 엔티티에 추가
	public static Conversation create(Npc npc, String playerName) {
		Conversation conversation = new Conversation();
		conversation.npc = npc;
		conversation.playerName = playerName;
		return conversation;
	}

	public void end() {
		this.endedAt  = LocalDateTime.now();
		this.npc.stopTalking();
	}
}
