package com.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEmail(){
        emailService.sendEmail("kaushikjain0608@gmail.com","Testing java mail sender","Hi, App kese hai");
    }
}
