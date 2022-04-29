package com.example.client.controller;

import com.example.client.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    private MyService myService;

    @PostMapping("/add")
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        return myService.add(a,b);
    }
}
