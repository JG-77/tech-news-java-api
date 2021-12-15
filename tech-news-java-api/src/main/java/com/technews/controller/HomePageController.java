// HomePageController serves as more of a navigator—essentially, it is the entry and exit point for users
// This controller is composed of @GetMapping annotation endpoints that help direct the flow of the API
package com.technews.controller;

import com.technews.repository.CommentRepository;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// class-level @Controller annotation to the HomePageController class,
// indicating that these controllers will control flow for the front-end user experience
@Controller
public class HomePageController {
    //@Autowired, to set up the relationships for the repositories
    //@Autowired informs Spring to scan for objects that need to be instantiated for a class or method to run
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CommentRepository commentRepository;
}