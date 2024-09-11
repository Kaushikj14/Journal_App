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

import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.JournalEntryService;
import com.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        String userName = authentication.getName();
        User user = userService.findUserByName(userName);

        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
            String userName = authentication.getName();

            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        String userName = authentication.getName();
        User user = userService.findUserByName(userName);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteById(myId,userName);

        if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // yaha tak tabhi aaya hoga jab autheticate ho gaya hoga
        String userName = authentication.getName();

        User user = userService.findUserByName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){

                JournalEntry old =  journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty() ? newEntry.getContent() : old.getContent());

                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

//        JournalEntry old = journalEntryService.findById(myId).orElse(null);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
