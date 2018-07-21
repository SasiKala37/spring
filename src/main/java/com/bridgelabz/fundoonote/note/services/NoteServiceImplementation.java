package com.bridgelabz.fundoonote.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (!optionalUser.isPresent()) {
			throw new NoteNotFoundException("note not found exception");
		}
		if (note.get().getUserId().equals(userId)) {
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
		note.setRemindMe(createNoteDTO.getCreateAt());
		note.setTitle(createNoteDTO.getTitle());
		note.setDescription(createNoteDTO.getDescription());
		noteRepository.save(note);
		return modelMapper.map(note, NoteDTO.class);

	}

	@Override
	public void deleteNote(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> note = checkUserNote(userId, noteId);
		if (!note.get().isTrash()) {
			throw new NoteNotFoundException("note do not trashed");
		}

		noteRepository.deleteById(noteId);

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
	public void trashNote(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		
		if (optionalNote.get().isTrash()) {

			Note note = optionalNote.get();
			note.setTrash(true);
			noteRepository.save(note);
		}

	}

	@Override
	public void addRemainder(String userId, String noteId, Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		
		Note note = optionalNote.get();
		note.setRemindMe(remaindAt);
		noteRepository.save(note);
	}

	@Override
	public void removeRemainder(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		
		Note note = optionalNote.get();
		note.setRemindMe(null);
		noteRepository.save(note);
	}

}
