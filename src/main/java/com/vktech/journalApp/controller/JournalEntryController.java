package com.vktech.journalApp.controller;

import com.vktech.journalApp.entity.JournalEntry;
import com.vktech.journalApp.entity.User;
import com.vktech.journalApp.service.JournalEntryService;
import com.vktech.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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


    @DeleteMapping("/id/{myId}")
    public  ResponseEntity<?>DeletebyId(@PathVariable String myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       boolean removed= journalEntryService.deleteById(new ObjectId(myId),userName);
        if(removed){
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }




    @PostMapping( )
    public ResponseEntity<JournalEntry>saveEntry(@RequestBody JournalEntry journalEntry)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            journalEntryService.saveEntry(journalEntry,userName);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }



    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user =userService.findUserByName(userName);
        List<JournalEntry> all = user.getJournalEntryList();
        if(all != null && !all.isEmpty())
        {
            return  new ResponseEntity<>(all,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry>getbyId(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findUserByName(userName);
        List<JournalEntry> collect = user.getJournalEntryList().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updating(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findUserByName(userName);
        List<JournalEntry> collect = user.getJournalEntryList().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old =journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
                journalEntryService.saveEntry(old);

                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        // 200 OK with updated data
    }

}
