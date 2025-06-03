package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.ChatDto;
import com.serpies.talk2me.model.CreateChatRequestDto;
import com.serpies.talk2me.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/create")
    public void createChat(@Payload CreateChatRequestDto createChatRequestDto, @Header("token") String token) {
        this.chatService.createChat(createChatRequestDto, token);
    }

    @GetMapping("/chats")
    @ResponseBody
    public List<ChatDto> getChats(@RequestHeader("Authorization") String token){
        return this.chatService.getAllChatsByUserId(token);
    }

}
