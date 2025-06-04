package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.ChatDto;
import com.serpies.talk2me.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat/create")
    @ResponseBody
    public ChatDto createChat(@RequestHeader("Authorization") String token, @RequestBody ChatDto chatDto) {
        return this.chatService.createChat(chatDto, token);
    }

    @GetMapping("/chats")
    @ResponseBody
    public List<ChatDto> getChats(@RequestHeader("Authorization") String token){
        return this.chatService.getAllChatsByUserId(token);
    }

}
