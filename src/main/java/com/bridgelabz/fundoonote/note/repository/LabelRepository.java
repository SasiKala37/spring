package com.bridgelabz.fundoonote.note.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bridgelabz.fundoonote.note.model.Label;
import java.util.List;
import java.lang.String;

public interface LabelRepository extends MongoRepository<Label, String> {

	void save(Optional<Label> label);

	Optional<Label> findByLabelName(String labelname);

	Optional<Label> findByLabelNameAndUserId(String labelName, String userId);

	//List<Label> findAllByLabelName(String userId);

	List<Label> findByUserId(String userId);

}
