package com.bridgelabz.fundoonote.note.services;

import java.util.Date;
import java.util.List;

import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.model.UpdateNoteDTO;
//@Service
public interface NoteService {
	NoteDTO createNote(CreateNoteDTO createNoteDTO,String token) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException;
	
	void deleteNote(String token,String noteId) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException;
	
	void updateNote(String token,UpdateNoteDTO updateNoteDTO) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException;
	
	List<NoteDTO> readNote(String token) throws  NoteNotFoundException, UserNotFoundException;
	
	public void trashNote(String token,String noteId) throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException; 
	
	public void addRemainder(String token,String noteId,Date remaindAt) throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException;
	
	public void removeRemainder(String token,String noteId) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException ;
}

