package com.wu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedController {


    @GetMapping("/")
    public String main(){
        return "redirect:/ProductCategory/list";
    }

}
