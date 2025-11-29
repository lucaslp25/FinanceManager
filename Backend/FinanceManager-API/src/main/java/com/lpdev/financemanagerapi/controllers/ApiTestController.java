package com.lpdev.financemanagerapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/test")
public class ApiTestController {

    @GetMapping
    public String apiTest(){
        return "If´u are seeing this message you´re authenticate!";
    }

}
