package com.bridgelabz.fundoonote.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.note.model.Label;
import com.bridgelabz.fundoonote.note.model.LabelDTO;
import com.bridgelabz.fundoonote.note.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	public void save(Optional<Note> note);

	Optional<Note> findByNoteId(String noteId);
	
	List<Note> findAllByUserId(String userId);
	/*@Query(value = "{noteId:?0, labelList.labelName:?1}")
	List<Note> findByUserIdAndLabelListInLabelName(String userId, String labelNmae);*/
	
	/*@Query(value="{'userId' : ?0,'labelList.labelName': ?1}")
	 List<Note> findAllByQuery(String userId, String labelName);*/
	
}
