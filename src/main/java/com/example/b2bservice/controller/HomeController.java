package com.example.b2bservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    // http://localhost:8080
    @GetMapping
    public String StartDisplay(){ // 프로젝트 첫 화면 진입
        return "/index.html";
    }

    @GetMapping("/home")
    public String enterHome(){ // 홈 진입
        return "/home.html";
    }

    @GetMapping("/signUp")
    public String enterSignUpForm(){
        return "/user-signup.html";
    }
}
