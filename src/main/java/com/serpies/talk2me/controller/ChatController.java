package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.ChatDto;
import com.serpies.talk2me.model.CreateChatRequestDto;
import com.serpies.talk2me.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/create")
    @SendToUser("/private/chat/create")
    public ChatDto createChat(@Payload CreateChatRequestDto createChatRequestDto, @Header("token") String token) {
        return chatService.createChat(createChatRequestDto, token);
    }

}
