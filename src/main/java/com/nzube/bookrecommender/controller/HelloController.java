package com.nzube.bookrecommender.controller;

import com.nzube.bookrecommender.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hello")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Hello Nzube";
    }

    @PostMapping
    public String greet(@RequestBody Person person) {
        return "Hello " + person.getName();
    }
}
