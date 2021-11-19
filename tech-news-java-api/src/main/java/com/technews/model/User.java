package com.technews.model;
//import annotations
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@Entity marks this as a persistable object, so that the User class can map to a table
@Entity
//@JsonIgnoreProperties specifies properties that should be ignored when serializing this object to JSON
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Table specifies the name of the table that this class maps to
@Table(name = "user")

public class User implements Serializable {
    @Id //used as the unique identifier
    @GeneratedValue(strategy = GenerationType.AUTO) //denotes that it will be a generated value
    private Integer id;

    private String username;

    @Column(unique = true) // signal that this value must be unique; duplicates won't be allowed
    private String email;

    private String password;

    @Transient //signals to Spring Data JPA that this data is NOT to be persisted in the database
    boolean loggedIn;

    //lists—collections of objects of the same type
    //@OneToMany, which will create the relationships between the tables automatically
    //EAGER, meaning that this list will gather all of its necessary information immediately after being created
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;
    // Need to use FetchType.LAZY to resolve multiple bags exception
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes;
    // Need to use FetchType.LAZY to resolve multiple bags exception
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;


    public User() {
    }

    public User(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //getter for Id
    public Integer getId() {
        return id;
    }
    //setter for Id
    public void setId(Integer id) {
        this.id = id;
    }
    //getter username
    public String getUsername() {
        return username;
    }
    //setter username
    public void setUsername(String username) {
        this.username = username;
    }
    //getter email
    public String getEmail() {
        return email;
    }
    //setter email
    public void setEmail(String email) {
        this.email = email;
    }
    //getter password
    public String getPassword() {
        return password;
    }
    //setter password
    public void setPassword(String password) {
        this.password = password;
    }
    //getter for LoggedIn
    public boolean isLoggedIn() {
        return loggedIn;
    }
    //setter loggedIn
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    //getter for Posts
    public List<Post> getPosts() {
        return posts;
    }
    //setter Posts
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    //getter Votes
    public List<Vote> getVotes() {
        return votes;
    }
    //setter Votes
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    //getter comments
    public List<Comment> getComments() {
        return comments;
    }
    //setter comments
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isLoggedIn() == user.isLoggedIn() &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getPosts(), user.getPosts()) &&
                Objects.equals(getVotes(), user.getVotes()) &&
                Objects.equals(getComments(), user.getComments());
    }

    //make sure to select "use getters for code generator"
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), isLoggedIn(), getPosts(), getVotes(), getComments());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                ", posts=" + posts +
                ", votes=" + votes +
                ", comments=" + comments +
                '}';
    }
}
