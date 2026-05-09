package com.nodeajva.maiv_java.controller;

import com.nodeajva.maiv_java.dto.request.ChatRequest;
import com.nodeajva.maiv_java.dto.response.NpcResponse;
import com.nodeajva.maiv_java.domain.Conversation;
import com.nodeajva.maiv_java.service.ConversationService;
import com.nodeajva.maiv_java.service.NpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VillageContoller {

    private final ConversationService conversationService;
    private final NpcService npcService;

    @GetMapping("/api/village/status")
    public Map<String, Object> getVillageStatus(@RequestParam(required = false) String map){

        List<NpcResponse> npcs = npcService.getAllNpc();

        List<Map<String, Object>> npcList = new ArrayList<>();
        for (NpcResponse npc : npcs){
            Map<String, Object> npcMap = new HashMap<>();
            npcMap.put("id", npc.id());
            npcMap.put("name", npc.name());
            npcMap.put("job", "주민");
            npcMap.put("action", "idle");
            npcList.add(npcMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("npcs", npcList);
        return response;
    }

    @PostMapping("/api/chat")
    public Map<String, Object> chat(@RequestBody ChatRequest request) throws  Exception{

        //대화 시작
        Conversation conversation = conversationService.findOrCreateConversation(
                request.npcId(), "Player"
        );

        //메세지 전송
        String reply = conversationService.sendMessage(
                conversation.getId(), request.message()
        );

        //응답 반환
        Map<String, Object> response = new HashMap<>();
        response.put("reply", reply);
        response.put("npc_id", request.npcId());
        return response;

    }

}
