package com.nodeajva.maiv_java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.dto.request.CreateNpcRequest;
import com.nodeajva.maiv_java.dto.response.NpcResponse;
import com.nodeajva.maiv_java.repository.NpcRepository;

@Service
public class NpcService {

	private final NpcRepository npcRepository;

	public NpcService(NpcRepository npcRepository) {
		this.npcRepository = npcRepository;
	}

	public List<NpcResponse> getAllNpc() {

		return npcRepository.findAll()
			.stream()
			.map(NpcResponse::from)
			.collect(Collectors.toList());
	}

	public NpcResponse getNpcbyId(Long id) {

		Npc npc = npcRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("NPC를 찿을수 없습니다"));
		return NpcResponse.from(npc);

	}

	public NpcResponse createNpc(CreateNpcRequest request) {

		Npc npc = Npc.create(request.name(), request.mbtiType());

		npcRepository.save(npc);
		return NpcResponse.from(npc);
	}

	public void deletNpc(Long id) {

		npcRepository.deleteById(id);
	}
}
