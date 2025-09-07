package com.vktech.journalApp.Repository;

import com.vktech.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserForSa()
    {
        Query query = new Query();
      query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
      query.addCriteria(Criteria.where("sentimenalAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
