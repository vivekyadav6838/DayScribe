package com.vktech.journalApp.Repository;


import com.vktech.journalApp.entity.ConfigJournalApp;
import com.vktech.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, ObjectId> {

}

