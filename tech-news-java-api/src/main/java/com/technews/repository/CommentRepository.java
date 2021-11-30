package com.technews.repository;

import com.technews.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //The method within the interface, named findAllCommentsByPostId(), will take a return type of List<Comment> and take an argument postId of value int
    List<Comment> findAllCommentsByPostId(int postId);
}