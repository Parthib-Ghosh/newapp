package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
   private UserRepo userrepo;

    private static PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private static Logger lg= LoggerFactory.getLogger(UserService.class);


    public boolean saveNewEntry(User user) {
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("user"));
        userrepo.save(user);
        return true;
    }
        catch(Exception e)
        {
          lg.info("error",e);
           return false;

        }
    }

    public void saveUser(User user)
    {
        userrepo.save(user);
    }

        public void save1(User user)
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User","Admin"));
            userrepo.save(user);



        }

    public List<User> getAll()
    {
        return userrepo.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userrepo.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        userrepo.deleteById(id);
    }

    public User findByname(String name)
    {

        return userrepo.findByname(name);

    }


}
