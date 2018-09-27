package com.naveen.samples.controller.push;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping(path = "/serviceWithPush")
    public String serviceWithPush(HttpServletRequest request,PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("resources/OnlineJavaPapers.png")
                .push();
        }
        return "index";
    }

    @GetMapping(path = "/serviceWithoutPush")
    public String serviceWithoutPush() {
        return "index";
    }
}
