package com.nodeajva.maiv_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodeajva.maiv_java.dto.request.CreateNpcRequest;
import com.nodeajva.maiv_java.dto.response.NpcResponse;
import com.nodeajva.maiv_java.service.NpcService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/npcs")
@RequiredArgsConstructor
public class NpcController {

	private final NpcService npcService;

	@GetMapping
	public List<NpcResponse> getAllNpcs() {
		return npcService.getAllNpc();
	}

	@GetMapping("/{id}")
	public NpcResponse getNpcById(@PathVariable Long id) {
		return npcService.getNpcbyId(id);

	}

	@PostMapping
	public NpcResponse ceateNpc(@RequestBody CreateNpcRequest request) {

		return npcService.createNpc(request);
	}

	@DeleteMapping("/{id}")
	public void delet(@PathVariable Long id) {
		npcService.deletNpc(id);
	}

}
