package com.serpies.talk2me.controller;

import com.serpies.talk2me.model.CreateMessageRequestDto;
import com.serpies.talk2me.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/message/create")
    public void createMessage(@Payload CreateMessageRequestDto createMessageRequestDto, @Header("token") String token){
        this.messageService.createMessage(createMessageRequestDto, token);
    }

}
