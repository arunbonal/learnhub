package com.learnhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class LearnHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnHubApplication.class, args);
    }
} 