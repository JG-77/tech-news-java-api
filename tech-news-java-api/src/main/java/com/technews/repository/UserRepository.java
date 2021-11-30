package com.technews.repository;

import org.springframework.stereotype.Repository;
import com.technews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//repository in Java is any class that fulfills the role of a data access object (DAO)
//it contains data retrieval, storage, and search functionality
@Repository

//To ensure that the repository can take User (from the entity model we created) and the id of that user, we specify an Integer
public interface UserRepository extends JpaRepository<User, Integer> { //extend the JpaRepository to ensure that the methods are still available via inheritance
    //This method will allow us to do what the name implies—find users by their email
    User findUserByEmail(String email) throws Exception;
}
