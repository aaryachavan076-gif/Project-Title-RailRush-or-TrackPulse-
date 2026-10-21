package com.railrush.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Experiment 12: Monolithic Application using Spring Boot.
 * Entry point for the RailRush enterprise monolithic service.
 */
@SpringBootApplication
public class RailRushSpringBootApplication {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("🚆 RailRush Spring Boot Monolithic Service v1.0.0");
        System.out.println("=================================================");
        SpringApplication.run(RailRushSpringBootApplication.class, args);
    }
}
