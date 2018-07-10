package com.bridgelabz.fundoonote.repository;



import org.bson.Document;

import com.bridgelabz.fundoonote.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase; 

public class UserDAO {
	public MongoCollection<Document> getCollection() {
		@SuppressWarnings("resource")
		MongoClient mongoClient=new MongoClient("localhost" , 27017);
		@SuppressWarnings("deprecation")
		DB dB=mongoClient.getDB("user");
		MongoCollection collection=(MongoCollection) dB.getCollection("register");	
		return collection;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveUser(User userDTO) {
		@SuppressWarnings("unused")
		BasicDBObject basicDBObject=new BasicDBObject()
		.append("id", userDTO.getId())
		.append("first_name", userDTO.getFirstName())
		.append("last_name", userDTO.getLastName())
		.append("email_id", userDTO.getEmailId())
		.append("user_name", userDTO.getUserName())
		.append("password", userDTO.getPassword());
		MongoCollection collection=getCollection();
		collection.insertOne(collection);
	}
	public User getUserByUserName(String userName) {
		MongoCollection<Document> collection=getCollection();
		//FindIterable<Document> iterDoc = collection.find({user_name:"userName"},{_id=0}); 
		return null;
	}
}
