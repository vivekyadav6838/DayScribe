package com.vktech.journalApp.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.vktech.journalApp.Repository.JournalEntryRepository;
import com.vktech.journalApp.Repository.UserRepository;
import com.vktech.journalApp.entity.JournalEntry;
import com.vktech.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    private  static  final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public boolean deleteById(ObjectId myId,String userName)
    {  boolean removed;
        try{
        User user=userRepository.findByUserName(userName);
         removed = user.getJournalEntryList().removeIf(x->x.getId().equals(myId));
        if(removed) {
            userService.saveUser(user);

            journalEntryRepository.deleteById(myId);
        }}
    catch (Exception e)
    {
        System.out.println(e);
        throw  new RuntimeException("An error ocuured while deleting the entry "+e);
    }
        return removed;
    }
    public Optional getById(ObjectId id)
    {
        return  journalEntryRepository.findById(id);
    }
    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }
    @Transactional
    public  void saveEntry(JournalEntry journalEntry,String userName)
    { try{
        User user=userService.findUserByName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntryList().add(saved);
        userService.saveUser(user);}
    catch (Exception e)
    {
       //e.printStackTrace();
        throw   new RuntimeException("An error ocuured",e);
    }
    }
    public  void saveEntry(JournalEntry journalEntry)
    {
       journalEntryRepository.save(journalEntry);
    }

}
