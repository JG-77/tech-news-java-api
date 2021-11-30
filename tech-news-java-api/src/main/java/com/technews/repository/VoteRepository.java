package com.technews.repository;

import com.technews.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    // instance-level annotation, @Query --> will take a single argument, which will be the specific query
    @Query("SELECT count(*) FROM Vote v where v.postId = :id")

    //method named countVotesByPostId() with a return type of int.
    // This method will have two arguments: method-level annotation of @Param("id"), & Integer id
    int countVotesByPostId(@Param("id") Integer id); // @Params("id") to use the id as a parameter,
}
