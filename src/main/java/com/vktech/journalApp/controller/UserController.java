package com.vktech.journalApp.controller;


import com.vktech.journalApp.Repository.UserRepository;
import com.vktech.journalApp.apiResponse.WeatherResponse;
import com.vktech.journalApp.entity.User;

import com.vktech.journalApp.service.UserService;

import com.vktech.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  WeatherService weatherService;




  @PutMapping
    public  ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
     User userInDb = userService.findUserByName(userName);

         userInDb.setUserName(user.getUserName());
         userInDb.setPassword(user.getPassword());
         userService.saveNewUser(userInDb);


        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public  ResponseEntity<?> deleteUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());



        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public  ResponseEntity<?> greetings()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse= weatherService.getWeather("AZAMGARH");
        String greetings="";
        if(weatherResponse!=null)
        {
            greetings = " Today's weather feels like "+weatherResponse.getCurrent().getFeelslike() +" F";

        }
        return  new ResponseEntity<>("Hi "+authentication.getName()+greetings,HttpStatus.OK);






    }





}
