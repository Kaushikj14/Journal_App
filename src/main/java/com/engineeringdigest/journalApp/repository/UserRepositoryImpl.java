package com.engineeringdigest.journalApp.repository;

import com.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate; // to interact with mongodb use mongotemplate


    public List<User> getUserForSA(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("vipul"));
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne("").ne(null));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true)
//            )
//        );
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
