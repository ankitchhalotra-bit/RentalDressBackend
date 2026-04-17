package com.custom_dress.demo.gateway.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetTime extends MessageController{

    @RequestMapping("/hello")
    public String getTime() {
        return "hello Ankit !!";
    }
}
