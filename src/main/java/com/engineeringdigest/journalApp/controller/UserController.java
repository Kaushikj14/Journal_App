//package com.engineeringdigest.journalApp.controller;
//
//import com.engineeringdigest.journalApp.entity.JournalEntry;
//import com.engineeringdigest.journalApp.service.JournalEntryService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
////    private Map<Long,JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public ResponseEntity<?> getAll(){
////        return new ArrayList<>(journalEntries.values());
//        List<JournalEntry> all = journalEntryService.getAll();
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
////            journalEntries.put(myEntry.getId(),myEntry);
//        try {
//            journalEntryService.saveEntry(myEntry);
//            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("id/{myId}")
//    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
//
//         Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//         if(journalEntry.isPresent()){
//                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("id/{myId}")
//    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
////        return journalEntries.remove(myId);
//        journalEntryService.deleteById(myId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("id/{myId}")
//    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry)
//    {
////        return journalEntries.put(myId,myEntry);
//        JournalEntry old = journalEntryService.findById(myId).orElse(null);
//        if(old!=null){
//            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals(" ")? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals(" ")? newEntry.getContent() : old.getContent());
//
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
////        return null;
//    }
//}
package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.api_response.WheatherResponse;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepository;
import com.engineeringdigest.journalApp.service.UserService;
import com.engineeringdigest.journalApp.service.WheatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WheatherService wheatherService;

//    @GetMapping
//    public List<User> findAll(){
//        return userService.getAll();
//    }
//
//


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        String userName = authentication.getName();
        User userInDb = userService.findUserByName(userName);
        if(userInDb != null)
        {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WheatherResponse WheaterResponse =  wheatherService.getWheather("Mumbai");
        String greetings = "";
        if(WheaterResponse!=null){
            greetings = " ,Wheather feels like "+WheaterResponse.getCurrent().getTempC();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+greetings,HttpStatus.OK);
    }



}
