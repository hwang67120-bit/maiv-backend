package com.nodeajva.maiv_java.repository;

import com.nodeajva.maiv_java.domain.Npc;
import com.nodeajva.maiv_java.domain.NpcState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NpcRepository extends JpaRepository<Npc, Long> {

    List<Npc> findByCurrentState(NpcState currentState);
}
