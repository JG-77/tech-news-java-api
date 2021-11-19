package com.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TemporalType;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String postUrl;

    @Transient
    private String userName;
    @Transient
    private int voteCount;
    private Integer userId;

    @NotNull //signals to the Spring Data JPA that this variable is not to be allowed to be null in the database
    @Temporal(TemporalType.DATE) //allows us to use the type Date in the database and signals to the JPA that these fields will house data of that type
    @Column(name = "posted_at") //designates the name of the column for the database
    private Date postedAt = new Date();

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
    private List<Comment> comments;
}
