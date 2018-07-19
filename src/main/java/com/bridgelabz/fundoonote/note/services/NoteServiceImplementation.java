package com.bridgelabz.fundoonote.note.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.exceptions.NoteException;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.user.util.Utility;


import io.jsonwebtoken.Claims;
@Service
public class NoteServiceImplementation implements NoteService {
	@Autowired
	NoteRepository noteRepository;
	@Autowired
	UserRepository userRepository;
	/*@Autowired
	ModelMapper modelMapper;*/
	
	private String existUser(String token) throws RegistrationException {
		Claims claim=Utility.parseJwt(token);
		Optional<User> optionalUser=userRepository.findById(claim.getId());
		if(!optionalUser.isPresent()) {
			throw new RegistrationException("User not exist");
		}
		return claim.getId();
		
	}
	@Override
	public void createNote(NoteDTO noteDTO,String token) throws RegistrationException, NoteException {
		String userId=existUser(token);
		if(noteDTO.getTitle()==""&&noteDTO.getDescription()==""&&noteDTO.getTitle()==null&&noteDTO.getDescription()==null) {
			throw new NoteException("Empty note should not be save");
		}
		
		Note note=new Note();
		Date date=new Date();
		
		note.setUserId(userId);
		note.setCreateAt(date);
		note.setTrash(false);
		note.setUpdateAt(date);
		note.setRemindMe(date);
		//modelMapper.map(note,NoteDTO.class);
		note.setTitle(noteDTO.getTitle());
		note.setDescription(noteDTO.getDescription());
		noteRepository.save(note);
	}

	@Override
	public void deleteNote(String token,String noteId) throws RegistrationException, NoteException {
		String userId=existUser(token);
		Optional<User> user=userRepository.findById(userId);
		Optional<Note> note=noteRepository.findById(noteId);
		if(!user.isPresent()) {
			throw new NoteException("User not found exception");
		}
		if(!note.isPresent()) {
			throw new NoteException("note not found exception");
		}
		
		noteRepository.deleteById(noteId);
		
	}

	@Override
	public void updateNote(String token,NoteDTO noteDTO,String noteId) throws RegistrationException, NoteException {
		String userId=existUser(token);
		Optional<User> user=userRepository.findById(userId);
		Optional<Note> optionalNote=noteRepository.findById(noteId);
		if(!user.isPresent()) {
			throw new NoteException("User not found exception");
		}
		if(!optionalNote.isPresent()) {
			throw new NoteException("note not found exception");
		}
		Note note=optionalNote.get();
		//modelMapper.map(note,NoteDTO.class);
		note.setTitle(noteDTO.getTitle());
		note.setDescription(noteDTO.getDescription());
		noteRepository.save(note);
			
	}

	@Override
	public List<Note> readNote(String token) throws RegistrationException, NoteException {
		String userId=existUser(token);
		Optional<User> user=userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new NoteException("User not found exception");
		}
		return noteRepository.findAll();
	}
 
	
}
