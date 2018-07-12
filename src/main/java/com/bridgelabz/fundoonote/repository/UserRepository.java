package com.bridgelabz.fundoonote.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.model.User;
@Repository
public interface UserRepository extends MongoRepository<User,String> {
 
	public  Optional<User> findByUserName(String userName);
    public Optional<User> findByEmailId(String emailId);
    public void save(Optional<User> user);
}
