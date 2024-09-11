//package com.engineeringdigest.journalApp.service;
//
//import com.engineeringdigest.journalApp.entity.User;
//import com.engineeringdigest.journalApp.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
////used for authorization
//@Component
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserName(username);
//        if(user!=null){
//            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getUserName())
//                    .password(user.getPassword())
//                    .roles(user.getRoles().toArray(new String[0]))
//                    .build();
//            return userDetails;
//
//        }
//        throw new UsernameNotFoundException("User Not Found with the username: "+username);
//    }
//}
package com.engineeringdigest.journalApp.service;

import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
}
