package com.technews.repository;


import com.technews.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // class-level annotation

// extend the JpaRepository and use <Post, Integer>
public interface PostRepository extends JpaRepository <Post, Integer>{
    //create the query method, named findAllPostsByUserId(Integer id), The return type will be List<Post>
    List<Post> findAllPostsByUserId(Integer id) throws Exception;
}
