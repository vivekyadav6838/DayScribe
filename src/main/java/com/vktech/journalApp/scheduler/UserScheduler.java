package com.vktech.journalApp.scheduler;

import com.vktech.journalApp.Repository.UserRepositoryImpl;
import com.vktech.journalApp.cache.AppCache;
import com.vktech.journalApp.entity.JournalEntry;
import com.vktech.journalApp.entity.User;
import com.vktech.journalApp.enums.Sentiment;
import com.vktech.journalApp.model.SentimentData;
import com.vktech.journalApp.service.EmailService;
import com.vktech.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private UserRepositoryImpl userRepositoryimpl;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache;





   @Scheduled(cron = "0 0 9 ? * SUN")
    public  void fethUserAndSendSaMail()
    {
        List<User> myList=userRepositoryimpl.getUserForSa();
        for(User user:myList)
        {
            List<JournalEntry> journalEntries =  user.getJournalEntryList();
            List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment:sentiments) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0) + 1);

                }
            }
                Sentiment mostFreqentSentimnet = null;
                int maxCount=0;
                for(Map.Entry<Sentiment,Integer>entry:sentimentCount.entrySet()){
                    if(entry.getValue()>maxCount)
                    {
                        maxCount=entry.getValue();
                        mostFreqentSentimnet=entry.getKey();
                    }
                }
                if(mostFreqentSentimnet!=null)
                {

                    SentimentData sentimentData= SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 day" + mostFreqentSentimnet).build();

                    try {
                        kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                    }
                    catch (Exception e)
                    {
                        emailService.sendMail(sentimentData.getEmail(),"Sentiment for last Week",sentimentData.getSentiment());
                    }

                }






        }

    }

    @Scheduled(cron = "0 0/30 * * * ?")
    private  void updateAppCache()
    {
        appCache.init();
    }

}
