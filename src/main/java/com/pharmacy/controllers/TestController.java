package com.pharmacy.controllers;

import com.pharmacy.payload.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/t")
    public ResponseEntity<Response> test(@RequestParam String param1){
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
        Response response = new Response(Response.Status.SUCCESS, "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
