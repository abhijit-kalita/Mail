package com.mycompany.springbootgmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:credentials.json")
public class SpringbootGmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGmailApplication.class, args);
    }
    
}
