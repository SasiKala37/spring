package com.bridgelabz.fundoonote.note.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.exceptions.NoteException;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.user.exceptions.RegistrationException;
//@Service
public interface NoteService {
	void createNote(NoteDTO noteDTO,String token) throws RegistrationException, NoteException;
	void deleteNote(String token,String noteId) throws RegistrationException, NoteException;
	void updateNote(String token,NoteDTO noteDTO,String noteId) throws RegistrationException, NoteException;
	List<Note> readNote(String token) throws RegistrationException, NoteException;
}

