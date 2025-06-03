package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.ChatDto;
import com.serpies.talk2me.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/message-files/{messageId}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@RequestHeader("Authorization") String token, @PathVariable("messageId") Long messageId){
        return this.fileService.getFileData(token, messageId);
    }

}
