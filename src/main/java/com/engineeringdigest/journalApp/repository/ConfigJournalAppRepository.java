package com.engineeringdigest.journalApp.repository;

import com.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import com.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
