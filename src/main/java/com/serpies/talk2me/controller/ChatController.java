package com.serpies.talk2me.controller;

import com.serpies.talk2me.model.CreateChatRequestDto;
import com.serpies.talk2me.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/create")
    public void createChat(@Payload CreateChatRequestDto createChatRequestDto, @Header("token") String token) {
        this.chatService.createChat(createChatRequestDto, token);
    }

}
