package com.example.ddmdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DdmdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdmdemoApplication.class, args);
    }

}
