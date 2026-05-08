package com.nodeajva.maiv_java.scheduler;

import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.domain.NpcState;
import com.nodeajva.maiv_java.repository.NpcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameScheduler {

    private final NpcRepository npcRepository;

    @Scheduled(fixedDelay = 10000)
    public void updateNpcBehavior(){

        List<Npc> idleNpcs = npcRepository.findByCurrentState(NpcState.IDLE);

        for (Npc npc : idleNpcs) {
            if (Math.random() < 0.2) {
                npc.getCurrentState();
                npcRepository.save(npc);
            }
        }

    }

    @Scheduled(fixedDelay = 30000)
    public void resetwaitingNpcs(){
        List<Npc> waitingNpcs  = npcRepository.findByCurrentState(NpcState.WAITING);

        for (Npc npc : waitingNpcs ) {
                npc.getCurrentState();
                npcRepository.save(npc);



        }
    }
}
