package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
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

public class JournalEntryController2 {


    @Autowired
    private JournalEntryService journalentryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesofUser()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        System.out.println("a");

        User user=userService.findByname(name);



        List<JournalEntry> all= user.getJournalEntries();

        if(all != null  && !all.isEmpty())
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

      @GetMapping("/id/{myId}")
        public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId myId)
        {

            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String d=authentication.getName();
            User user=userService.findByname(d);

            List<JournalEntry> h=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
               if(!h.isEmpty()) {
                   Optional<JournalEntry> entry = journalentryService.findById(myId);

                   if (entry.isPresent()) {
                       return new ResponseEntity<>(entry.get(), HttpStatus.OK);
                   }
               }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }
    
        @DeleteMapping("id/{myId}")
        public ResponseEntity<JournalEntry>  deleteById(@PathVariable ObjectId myId)
        {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String d = authentication.getName();


                boolean re = journalentryService.deleteById(myId, d);
                if (re == true) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry( @RequestBody JournalEntry entry)
    {
        try{
         System.out.println("d");
            entry.setDate(LocalDateTime.now());
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String name=authentication.getName();



            journalentryService.saveEntry(entry,name);

             return new ResponseEntity<>(entry,HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            System.out.println(e);
            return new ResponseEntity<>(entry,HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry entry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        User user=userService.findByname(name);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry=journalentryService.findById(myId);
            if(journalEntry.isPresent())
            {
                    JournalEntry entry1 = journalentryService.findById(myId).orElse(null);

                if(entry1!=null)
                {
                    entry1.setTitle(entry.getTitle()!=null && !entry1.getTitle().equals("") ? entry.getTitle() : entry1.getTitle());
                    entry1.setContent(entry.getContent()!=null && !entry1.getTitle().equals("")? entry.getContent() : entry1.getContent());
                    entry1.setDate(LocalDateTime.now());
                    journalentryService.saveEntry(entry1);

                    return new ResponseEntity<>(entry1,HttpStatus.OK);

                }
            }


        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


}
