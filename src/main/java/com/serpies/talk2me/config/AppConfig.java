package com.serpies.talk2me.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties", "classpath:db.properties", "classpath:config.properties"})
@ComponentScan(basePackages = {
    "com.serpies.talk2me"
})
public class AppConfig {
}