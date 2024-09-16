package com.engineeringdigest.journalApp.schedular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulersTests {

    @Autowired
    private  UserSchedular userSchedular;

    @Test
    public void testFetchUsersAndSendMail(){
        userSchedular.fetchUsersAndSendSaMail();
    }
}
