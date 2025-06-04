package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/message/create")
    @ResponseBody
    public MessageDto createMessage(@RequestHeader("Authorization") String token, @RequestBody MessageDto messageDto){
        return this.messageService.createMessage(messageDto, token);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<MessageDto> getMessages(@RequestHeader("Authorization") String token){
        return this.messageService.getAllMessagesByUser(token);
    }

}
