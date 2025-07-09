package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repository.Repo;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private Repo repo;

    @Autowired
    private UserService userservice;


    public List<JournalEntry> getAll()
    {
        return repo.findAll();

    }

    @Transactional
   public void saveEntry(JournalEntry journalEntry,String name)
   {
       User user=userservice.findByname(name);

       JournalEntry journalentry1= repo.save(journalEntry);

       user.getJournalEntries().add(journalentry1);

       userservice.saveUser(user);


   }

   public void saveEntry(JournalEntry journalEntry)
   {
        repo.save(journalEntry);
   }

   public Optional<JournalEntry> findById(ObjectId myId)
   {
       return repo.findById(myId);
   }

   @Transactional
   public boolean deleteById(ObjectId myId,String name)
   {
       boolean b=false;
     try {
         User user = userservice.findByname(name);

          b = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
         if (b == true) {
             userservice.saveUser(user);
             repo.deleteById(myId);
         }
     }
     catch(Exception e)
     {
         System.out.println(e);

     }

     return b;



   }




}
