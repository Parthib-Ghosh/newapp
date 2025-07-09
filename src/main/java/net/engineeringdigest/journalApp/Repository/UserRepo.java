package net.engineeringdigest.journalApp.Repository;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends  MongoRepository<User, ObjectId> {

    User findByname(String name);

    void deleteByname(String name);



}
