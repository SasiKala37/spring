package com.bridgelabz.fundoonote.note.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.note.model.Note;
@Repository
public interface NoteRepository extends MongoRepository<Note, String>{
	public void save(Optional<Note> note);
	
	
}
