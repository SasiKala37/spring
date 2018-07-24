package com.bridgelabz.fundoonote.note.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonote.note.model.Label;
import java.lang.String;
import java.util.List;

public interface LabelRepository extends MongoRepository<Label, String> {
		void save(Optional<Label> label);
		Optional<Label> findByLabelName(String labelname);
		
}
