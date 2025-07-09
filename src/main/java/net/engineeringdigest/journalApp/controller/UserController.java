package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/users")
public class UserController {

     @Autowired
    private UserService userService;

     private UserRepo userRepo;

    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAll();
    }

    @PostMapping
    public void createuser(@RequestBody User user)
    {
        userService.saveNewEntry(user);
    }

      @PutMapping
      public ResponseEntity<?> updateUser(@RequestBody User user)
      {

          Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
          String name=authentication.getName();


          User userindb=userService.findByname(name);


              userindb.setName(user.getName());
              userindb.setPassword(user.getPassword());
               userService.saveNewEntry(userindb);


          return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      }

      @DeleteMapping
      public ResponseEntity<?> deleteUserbyid()
      {
          Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
          userRepo.deleteByname(authentication.getName());

          return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      }





}
