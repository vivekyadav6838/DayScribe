package com.vktech.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private  EmailService emailService;

    @Test
    void emailtest()
    {
    emailService.sendMail("v.vivek6838@gmail.com","::testing mail","Hi how are you ");

    }
}
