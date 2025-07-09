package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServicebo implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = userRepo.findByname(username);

      if(user!=null)
      {

            UserDetails userDetails= org.springframework.security.core.userdetails.User.builder().username(user.getName()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();

            return userDetails;

        }
       throw new UsernameNotFoundException("User not found"+username);



    }

}
