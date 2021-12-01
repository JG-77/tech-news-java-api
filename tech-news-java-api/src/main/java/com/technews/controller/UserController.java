package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //will allow it to process JSON/XML responses and send JSON/XML objects via the API
public class UserController {
    //CRUD -> create, read, update, and delete

    //tells Spring to scan the project for objects that will need to be instantiated for a class or method to run
    @Autowired //informs Spring to only instantiate each object as needed by the program
    UserRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/api/users") //combines the route ("/api/users") and the type of HTTP method used (GET)
    //we set the return type to List<User>, because we want it to return a list of users
    public List<User> getAllUsers() {
        //Calling the inherited findAll() method on the repository object, we can get a list of users and assign it to the userList variable
        List<User> userList = repository.findAll();
        //call the getPosts() function for every User, assigned to the variable u inside userList
        for (User u : userList) {
            List<Post> postList = u.getPosts();
            for (Post p : postList) {
                //we'll iterate over each post, invoking the setVoteCount() method,
                // passing in the countVotesByPostId() method,
                // and finally using getId() to obtain the id of the post
                p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            }
        }
        //Instead of returning a list, the getUserById() method will only return a single user
        return userList;
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        User returnUser = repository.getOne(id);
        List<Post> postList = returnUser.getPosts();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        //method will only return a single user
        return returnUser;
    }

    @PostMapping("/api/users") //will allow us to add a user to the database
    //we pass in the @RequestBody annotation
    //which will map the body of this request to a transfer object,
    //then deserialize the body onto a Java object for easier use
    public User addUser(@RequestBody User user) {
        // Encrypt password
        //setPassword() method offers protection to users
        //use BCrypt to encrypt the passwords for new users
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        // After encryption, we save the new user and then return the newly created user
        repository.save(user);
        return user;
    }

    @PutMapping("/api/users/{id}") //allows us to update a user based on a specific id
    //@PathVariable will allow us to enter the int id into the request URI as a parameter,
    // replacing the /{id} in @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        User tempUser = repository.getOne(id);

        if (!tempUser.equals(null)) {
            user.setId(tempUser.getId());
            repository.save(user);
        }
        return user;
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // use the @PathVariable to pass in an int to the request URI
    public void deleteUser(@PathVariable int id) {
        //based on that specific id, we'll delete the associated user
        repository.deleteById(id);
    }
}
