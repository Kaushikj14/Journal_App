package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import com.engineeringdigest.journalApp.service.UserService;
import com.engineeringdigest.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String greet(){
        return "Everything Working good";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.saveNewUser(user);
//        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception occured while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.NOT_FOUND);
        }

    }
}
