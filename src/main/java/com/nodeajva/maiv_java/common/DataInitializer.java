package com.nodeajva.maiv_java.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.repository.NpcRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private final NpcRepository npcRepository;

	@Override
	public void run(ApplicationArguments arg) {
		if (npcRepository.count() == 0) {
			npcRepository.save(Npc.create("에릭", "INTJ"));
			npcRepository.save(Npc.create("마리아", "ENFP"));
			npcRepository.save(Npc.create("한스", "ISTJ"));
			npcRepository.save(Npc.create("그레타", "ENFJ"));
		}

	}
}
