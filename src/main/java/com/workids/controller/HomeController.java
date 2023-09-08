package com.workids.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HomeController {

    @RestController
    public class CircularViewPathController {

        @GetMapping("/path")
        public String path() {
            return "path";
        }
    }
}
