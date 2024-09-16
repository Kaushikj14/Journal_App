package com.engineeringdigest.journalApp.schedular;

import com.engineeringdigest.journalApp.cache.AppCache;
import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import com.engineeringdigest.journalApp.service.EmailService;
import com.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
            List< User> users = userRepository.getUserForSA();
            for(User user: users){
                  List<JournalEntry> journalEntries =  user.getJournalEntries();
                List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
                String entry = String.join(" ", filteredEntries);
                String sentiment = sentimentAnalysisService.getSentiment(entry);
                emailService.sendEmail(user.getEmail(),"Sentiment for Last Seven Days",sentiment);

            }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
