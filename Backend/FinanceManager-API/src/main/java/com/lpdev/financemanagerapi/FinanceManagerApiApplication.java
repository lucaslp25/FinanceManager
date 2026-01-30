package com.lpdev.financemanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FinanceManagerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceManagerApiApplication.class, args);
    }

}
