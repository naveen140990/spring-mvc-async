package com.naveen.samples.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping(path = "/error")
    public String greeting() {
        return "myerror";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
