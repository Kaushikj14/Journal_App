package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String greet(){
        return "Everything Working good";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
//        userService.saveNewUser(user);
    }
}
