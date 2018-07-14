package com.bridgelabz.fundoonote.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonote.user.model.User;


public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByEmailId(String emailId);

	public void save(Optional<User> user);
}
