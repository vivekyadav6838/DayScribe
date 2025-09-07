package com.vktech.journalApp.controller;

import com.vktech.journalApp.entity.User;
import com.vktech.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?>getAll()
    {
        List<User> lst =userService.getAll();
        if(!lst.isEmpty())
            return  new ResponseEntity<>(lst, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   @PostMapping("/create-admin-user")
    public void addAdmin(@RequestBody User user)
    {
        userService.saveAdmin(user);
    }
}
