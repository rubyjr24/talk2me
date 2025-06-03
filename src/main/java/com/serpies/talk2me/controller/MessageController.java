package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.model.CreateMessageRequestDto;
import com.serpies.talk2me.service.MessageService;
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
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/message/create")
    public void createMessage(@Payload CreateMessageRequestDto createMessageRequestDto, @Header("token") String token){
        this.messageService.createMessage(createMessageRequestDto, token);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<MessageDto> getMessages(@RequestHeader("Authorization") String token){
        return this.messageService.getAllMessagesByUser(token);
    }

}
