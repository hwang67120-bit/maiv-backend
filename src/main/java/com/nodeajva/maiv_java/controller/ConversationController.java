package com.nodeajva.maiv_java.controller;

import com.nodeajva.maiv_java.dto.response.ConversationResponse;
import com.nodeajva.maiv_java.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;


    @PostMapping("/start")
    public ConversationResponse startConversation(@RequestParam Long npcId, @RequestParam String playerName) {

        return ConversationResponse.from(
                conversationService.startConversation(npcId, playerName)

        );
    }

    @PostMapping("/{id}/message")
    public String sendMessage(
            @PathVariable  Long id,
            @RequestParam String playerMessage) {

      return conversationService.sendMessage(id, playerMessage);
    }

    @PostMapping("/{id}/end")
    public void endConversation(@PathVariable Long id){
        conversationService.endConversation(id);

    }


}
