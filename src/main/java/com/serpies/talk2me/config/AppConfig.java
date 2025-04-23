package com.serpies.talk2me.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.serpies.talk2me.controllers",
    "com.serpies.talk2me.services",
    "com.serpies.talk2me.db.repositories"
})
public class AppConfig {
}