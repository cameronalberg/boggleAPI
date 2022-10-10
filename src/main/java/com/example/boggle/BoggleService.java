package com.example.boggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoggleService {

    public static void main(String[] args) {
        SpringApplication.run(BoggleService.class, args);
        System.out.println("initialization successful");
    }

}
