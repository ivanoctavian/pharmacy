package com.pharmacy.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/configserver")
public class TestController {

    @Value("${db.url: default no value!}")
    public String dbCon;
    @GetMapping(value = "/db-value")
    public String getDatabaseUrl(){
        return dbCon;
    }
}
