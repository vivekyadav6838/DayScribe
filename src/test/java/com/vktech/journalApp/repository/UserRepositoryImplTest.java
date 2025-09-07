package com.vktech.journalApp.repository;

import com.vktech.journalApp.Repository.UserRepositoryImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Disabled
    @Test
    public void  testUserExits()
    {
        userRepositoryImpl.getUserForSa();
    }
}
