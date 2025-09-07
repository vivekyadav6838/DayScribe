package com.vktech.journalApp.service;


import com.vktech.journalApp.Repository.UserRepository;

import com.vktech.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private  static  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void deleteById(ObjectId myId)
    {
        userRepository.deleteById(myId);
    }
    public Optional getById(ObjectId id)
    {
        return  userRepository.findById(id);
    }
    public List<User> getAll()
    {
        return userRepository.findAll();
    }
    public  void saveUser(User user)
    {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public  boolean saveNewUser(User user)
    {
        try{
        user.setRoles(Arrays.asList("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;} catch (Exception e) {
          //  log.info("HAHHAHAHAAH");
            return false;
        }
    }
    public  User findUserByName(String userName)
    {
         return userRepository.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList("USER","ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
