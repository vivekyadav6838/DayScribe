package com.vktech.journalApp.service;

import com.vktech.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private  EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments",groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData)
    {
        sendEmail(sentimentData);
    }

    public  void  sendEmail(SentimentData sentimentData)
    {
        emailService.sendMail(sentimentData.getEmail(),"Sentiment for previous week",sentimentData.getSentiment());
    }

}
