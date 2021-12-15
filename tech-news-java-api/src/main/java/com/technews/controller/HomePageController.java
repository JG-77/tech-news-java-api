// HomePageController serves as more of a navigator—essentially, it is the entry and exit point for users
// This controller is composed of @GetMapping annotation endpoints that help direct the flow of the API
package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.CommentRepository;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    //added the login endpoint, which will allow users to log in by calling the login.html template when the /login route is hit
    @GetMapping("/login")
    //Model model is inherited functionality from Spring—we've remapped Model to the model variable
    public String login(Model model, HttpServletRequest request) {

        if (request.getSession(false) != null) {
            return "redirect:/";
        }
    //addAttribute() method, built into the Model object, sends information to the Thymeleaf templates
    //model.addAttribute() will send a newly created user to the template as the string user
        model.addAttribute("user", new User());
        return "login";
    }
    //logout route is hit, we check whether the session exists
    //If it does, we'll invalidate the session, subsequently logging out the user
    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }

    //\This endpoint will show users the homepage, which will require it to retrieve all of the Post data
    @GetMapping("/")
    public String homepageSetup(Model model, HttpServletRequest request) {
        User sessionUser = new User();
        // inside the if conditional, we check that the user is logged in
        if (request.getSession(false) != null) {
            sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            model.addAttribute("loggedIn", sessionUser.isLoggedIn());
        } else {
            model.addAttribute("loggedIn", false);
        }

        //create a variable called postList with a type List<Post>
        List<Post> postList = postRepository.findAll();
        //use a for loop to get all of the posts and populate them into the postList variable
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            User user = userRepository.getOne(p.getUserId());
            p.setUserName(user.getUsername());
        }
        // use model.addAttributes() to add these details to the User model
        model.addAttribute("postList", postList);
        model.addAttribute("loggedIn", sessionUser.isLoggedIn());
        model.addAttribute("point", "point");
        model.addAttribute("points", "points");
        //As a result, this route will populate and display the homepage.html template
        return "homepage";
    }

    @GetMapping("/dashboard")
    public String dashboardPageSetup(Model model, HttpServletRequest request) throws Exception {

        if (request.getSession(false) != null) {
            setupDashboardPage(model, request);
            return "dashboard";
        } else {
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @GetMapping("/dashboardEmptyTitleAndLink")
    public String dashboardEmptyTitleAndLinkHandler(Model model, HttpServletRequest request) throws Exception {
        setupDashboardPage(model, request);
        model.addAttribute("notice", "To create a post the Title and Link must be populated!");
        return "dashboard";
    }


    @GetMapping("/singlePostEmptyComment/{id}")
    public String singlePostEmptyCommentHandler(@PathVariable int id, Model model, HttpServletRequest request) {
        setupSinglePostPage(id, model, request);
        model.addAttribute("notice", "To add a comment you must enter the comment in the comment text area!");
        return "single-post";
    }


    @GetMapping("/post/{id}")
    public String singlePostPageSetup(@PathVariable int id, Model model, HttpServletRequest request) {
        setupSinglePostPage(id, model, request);
        return "single-post";
    }


    @GetMapping("/editPostEmptyComment/{id}")
    public String editPostEmptyCommentHandler(@PathVariable int id, Model model, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            setupEditPostPage(id, model, request);
            model.addAttribute("notice", "To add a comment you must enter the comment in the comment text area!");
            return "edit-post";
        } else {
            model.addAttribute("user", new User());
            return "login";
        }
    }


    @GetMapping("/dashboard/edit/{id}")
    public String editPostPageSetup(@PathVariable int id, Model model, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            setupEditPostPage(id, model, request);
            return "edit-post";
        } else {
            model.addAttribute("user", new User());
            return "login";
        }
    }

    //All the methods associated with the dashboard page will be called by the setupDashboardPage()
    public Model setupDashboardPage(Model model, HttpServletRequest request) throws Exception {
        User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
        //assign the value of the current user, via SESSION_USER, to a variable called sessionUser
        //get the user's id and store that in userId
        Integer userId = sessionUser.getId();

        List<Post> postList = postRepository.findAllPostsByUserId(userId);
        //use a for loop to gather all posts that have been made by the specific userId
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            User user = userRepository.getOne(p.getUserId());
            p.setUserName(user.getUsername());
        }

        //we're sending the current user (sessionUser) to the Thymeleaf dashboard template as a variable called user
        model.addAttribute("user", sessionUser);
        model.addAttribute("postList", postList);
        model.addAttribute("loggedIn", sessionUser.isLoggedIn());
        model.addAttribute("post", new Post());

        return model;
    }

    //code for setupSinglePostPage() and setupEditPostPage() closely resembles the code for setting up the dashboard page
    public Model setupSinglePostPage(int id, Model model, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            model.addAttribute("sessionUser", sessionUser);
            model.addAttribute("loggedIn", sessionUser.isLoggedIn());
        }

        Post post = postRepository.getOne(id);
        post.setVoteCount(voteRepository.countVotesByPostId(post.getId()));

        User postUser = userRepository.getOne(post.getUserId());
        post.setUserName(postUser.getUsername());

        List<Comment> commentList = commentRepository.findAllCommentsByPostId(post.getId());

        model.addAttribute("post", post);

        model.addAttribute("commentList", commentList);
        model.addAttribute("comment", new Comment());

        return model;
    }

    //code for setupSinglePostPage() and setupEditPostPage() closely resembles the code for setting up the dashboard page
    public Model setupEditPostPage(int id, Model model, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");

            Post returnPost = postRepository.getOne(id);
            User tempUser = userRepository.getOne(returnPost.getUserId());
            returnPost.setUserName(tempUser.getUsername());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));

            List<Comment> commentList = commentRepository.findAllCommentsByPostId(returnPost.getId());

            model.addAttribute("post", returnPost);
            model.addAttribute("loggedIn", sessionUser.isLoggedIn());
            model.addAttribute("commentList", commentList);
            model.addAttribute("comment", new Comment());
        }

        return model;
    }
}