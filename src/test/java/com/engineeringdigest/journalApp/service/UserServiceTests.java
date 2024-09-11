package com.engineeringdigest.journalApp.service;

import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // to start our application context (If we don't do that we will get null pointer exception and the reasoon is our main application is not started)
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

//    method ka naam kuch bhi ho sakta hai
//    @Test
////    yaha hum kuch test karenge
//    public void testadd(){
//
//        assertEquals(expected:4,actual:2+2);
//    }

    @Disabled // if we don't want to run this test
    @Test
    public void testFindByUserName(){
//        assertNotNull(userRepository.findByUserName("ram"),"failed for :"+name);
        User user = userRepository.findByUserName("ram");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
