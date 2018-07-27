package com.bridgelabz.fundoonote.note.services;

import java.util.Date;
import java.util.List;

import com.bridgelabz.fundoonote.note.exceptions.DateNotProperlySetException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNameAlreadyInUseException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;
import com.bridgelabz.fundoonote.note.model.LabelDTO;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.model.UpdateNoteDTO;

//@Service
public interface NoteService {

	NoteDTO createNote(CreateNoteDTO createNoteDTO, String token)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException, LabelNotFoundException;

	void deleteNote(String token, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException;

	void updateNote(String token, UpdateNoteDTO updateNoteDTO)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException;

	List<NoteDTO> readNote(String token) throws NoteNotFoundException, UserNotFoundException;

	public void trashNote(String token, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException;

	public void addRemainder(String token, String noteId, Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperlySetException;

	public void removeRemainder(String token, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException;

	public void restoreNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public List<NoteDTO> getAllTrashNotes(String userId) throws UserNotFoundException;

	public List<NoteDTO> getAllArchiveNotes(String userId) throws UserNotFoundException;

	public void setArchiveNotes(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public void changeColor(String userId, String noteId, String color)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public void unArchiveNotes(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public void pinNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public void unPinNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException;

	public String createLabel(String userId, String labelName)
			throws LabelNotFoundException, UserNotFoundException, LabelNameAlreadyInUseException;

	public List<LabelDTO> getAllLabels(String userId) throws UserNotFoundException;

	public void deleteLabel(String userId, String labelName)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException, NoteNotFoundException;

	public void addLabel(String userId, LabelDTO labelDTO, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException, LabelNameAlreadyInUseException;
	
	public void renameLabel(String userId, String oldLabelName,String newLabelName)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException ;

}
