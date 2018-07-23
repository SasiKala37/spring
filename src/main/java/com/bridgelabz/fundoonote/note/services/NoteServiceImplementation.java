package com.bridgelabz.fundoonote.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.exceptions.DateNotProperSetException;
import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.model.UpdateNoteDTO;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.note.util.Utility;

@Service
public class NoteServiceImplementation implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	private Optional<Note> checkUserNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not exist");
		}
		Optional<Note> note = noteRepository.findById(noteId);
		if (!note.isPresent()) {
			throw new NoteNotFoundException("note not found exception");
		}
		if (!note.get().getUserId().equals(userId)) {
			throw new UnAuthorizedException("user doesnot has the note vice versa");
		}
		return note;
	}

	@Override
	public NoteDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws UserNotFoundException, NoteCreationException, NoteNotFoundException, UnAuthorizedException {

		Utility.validateTitleAndDesc(createNoteDTO.getTitle(), createNoteDTO.getDescription());

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not exist");
		}

		Note note = new Note();

		note.setUserId(userId);
		note.setCreateAt(createNoteDTO.getCreateAt());
		note.setUpdateAt(createNoteDTO.getCreateAt());
		note.setRemindAt(createNoteDTO.getCreateAt());
		note.setTitle(createNoteDTO.getTitle());
		note.setDescription(createNoteDTO.getDescription());
		noteRepository.save(note);
		return modelMapper.map(note, NoteDTO.class);

	}

	@Override
	public List<NoteDTO> readNote(String userId) throws NoteNotFoundException, UserNotFoundException {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not exist");
		}

		List<Note> note = noteRepository.findAll();

		List<NoteDTO> listNoteDTO = new ArrayList<>();
		NoteDTO noteDTO = null;
		for (int i = 0; i < note.size(); i++) {
			noteDTO = modelMapper.map(note.get(i), NoteDTO.class);
			listNoteDTO.add(noteDTO);
		}

		return listNoteDTO;
	}

	@Override
	public void updateNote(String userId, UpdateNoteDTO updateNoteDTO)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException {

		Utility.validateTitleAndDesc(updateNoteDTO.getTitle(), updateNoteDTO.getDescription());
		Optional<Note> optionalNote = checkUserNote(userId, updateNoteDTO.getNoteId());

		if (optionalNote.get().isTrash()) {
			throw new NoteNotFoundException("Note not found");
		}

		Note note = optionalNote.get();

		note.setTitle(updateNoteDTO.getTitle());
		note.setDescription(updateNoteDTO.getDescription());
		note.setUpdateAt(updateNoteDTO.getUpdateAt());
		noteRepository.save(note);

	}

	@Override
	public void deleteNote(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (optionalNote.get().isTrash()) {
			throw new NoteNotFoundException("note  not found Exception");
		}

		Note note = optionalNote.get();
		note.setTrash(true);
		noteRepository.save(note);

	}

	@Override
	public void trashNote(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		if (optionalNote.get().isTrash()) {
			noteRepository.deleteById(noteId);
		}
	}

	@Override
	public void restoreNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		if (optionalNote.get().isTrash()) {
			Note note = optionalNote.get();
			note.setTrash(false);

			noteRepository.save(note);

		}
	}

	@Override
	public void addRemainder(String userId, String noteId, Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperSetException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (remaindAt.before(new Date())) {
			throw new DateNotProperSetException("set the date rather than todays date");
		}

		Note note = optionalNote.get();
		note.setRemindAt(remaindAt);
		noteRepository.save(note);
	}

	@Override
	public void removeRemainder(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		Note note = optionalNote.get();
		note.setRemindAt(null);
		noteRepository.save(note);
	}

	@Override
	public List<NoteDTO> getAllTrashNotes(String userId) throws UserNotFoundException {

		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Note> optionalNote = noteRepository.findAll();
		List<NoteDTO> listNoteDTO = new ArrayList<>();

		for (int i = 0; i < optionalNote.size(); i++) {
			if (optionalNote.get(i).isTrash()) {
				listNoteDTO.add(modelMapper.map(optionalNote.get(i), NoteDTO.class));
			}
		}
		return listNoteDTO;
	}

	@Override
	public void setArchiveNotes(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (optionalNote.get().isArchive()) {
			throw new NoteNotFoundException("note  not found Exception");
		}

		Note note = optionalNote.get();
		note.setArchive(true);
		noteRepository.save(note);
	}
    @Override
	public void unArchiveNotes(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (!optionalNote.get().isArchive()) {
			Note note = optionalNote.get();
			note.setArchive(true);
			noteRepository.save(note);
		}
	}

	@Override
	public List<NoteDTO> getAllArchiveNotes(String userId) throws UserNotFoundException {
		
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Note> optionalNote = noteRepository.findAll();
		List<NoteDTO> listNoteDTO = new ArrayList<>();

		for (int i = 0; i < optionalNote.size(); i++) {
			if (optionalNote.get(i).isArchive()) {
				listNoteDTO.add(modelMapper.map(optionalNote.get(i), NoteDTO.class));
			}
		}
		return listNoteDTO;

	}

	@Override
	public void changeColor(String userId, String noteId, String color)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (color == null || color.equals("")) {
			optionalNote.get().setColor("white");
			noteRepository.save(optionalNote.get());
		} else {
			optionalNote.get().setColor(color);
			noteRepository.save(optionalNote.get());
		}
	}
	@Override
	public void pinNote(String userId,String noteId) throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		
		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		
		if(!optionalNote.get().isPin()) {
			optionalNote.get().setPin(true);
			noteRepository.save(optionalNote.get());
		}
		
	}
	
	@Override
	public void unPinNote(String userId,String noteId) throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		
		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		
		if(optionalNote.get().isPin()) {
			optionalNote.get().setPin(true);
			noteRepository.save(optionalNote.get());
		}
	}
	
	

}
