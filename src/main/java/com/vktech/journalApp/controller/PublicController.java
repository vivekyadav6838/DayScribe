package com.vktech.journalApp.controller;

import com.vktech.journalApp.entity.User;
import com.vktech.journalApp.service.UserService;
import com.vktech.journalApp.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    public UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public  void singUp(@RequestBody User newUser) {
        userService.saveNewUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User newUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUser.getUserName(), newUser.getPassword()));
            UserDetails userDetails= userDetailsService.loadUserByUsername(newUser.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.error("Execpetion occure while gerneating authenticationToken");
            return  new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/health-status")
    public String healthCheck()
    {
        return  "OK";
    }
}
