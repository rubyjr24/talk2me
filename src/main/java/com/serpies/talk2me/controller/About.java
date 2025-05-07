package com.serpies.talk2me.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class About {

    @GetMapping
    public String holaMundo(){
        return "Hola Mundo!!";
    }

}
