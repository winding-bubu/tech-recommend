package com.tech.recommend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tech.recommend.*"})
public class TechRecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechRecommendApplication.class, args);
    }

}
