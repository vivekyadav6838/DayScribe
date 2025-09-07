package com.vktech.journalApp.cache;

import com.vktech.journalApp.Repository.ConfigJournalAppRepository;
import com.vktech.journalApp.entity.ConfigJournalApp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public  void init()
    {
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        for(ConfigJournalApp configJournalApp:all)
        {
            APP_CACHE.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }
}
