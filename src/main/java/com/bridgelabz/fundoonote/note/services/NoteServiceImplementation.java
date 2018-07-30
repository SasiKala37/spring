package com.bridgelabz.fundoonote.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.exceptions.DateNotProperlySetException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNameAlreadyExistException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.model.UpdateNoteDTO;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;
import com.bridgelabz.fundoonote.note.model.Label;
import com.bridgelabz.fundoonote.note.model.LabelDTO;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.LabelRepository;
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
	private LabelRepository labelRepository;

	@Autowired
	private ModelMapper modelMapper;

	private Optional<Note> checkUserNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not exist");
		}
		Optional<Note> note = noteRepository.findById(noteId);
		// System.out.println("NoteId:"+note.get().getNoteId());
		if (!note.isPresent()) {
			throw new NoteNotFoundException("note not found exception");
		}
		if (!note.get().getUserId().equals(userId)) {
			throw new UnAuthorizedException("user doesnot has the note vice versa");
		}
		return note;
	}

	@Override
	public NoteDTO createNote(CreateNoteDTO createNoteDTO, String userId) throws UserNotFoundException,
			NoteCreationException, NoteNotFoundException, UnAuthorizedException, LabelNotFoundException {

		Utility.validateTitleAndDesc(createNoteDTO.getTitle(), createNoteDTO.getDescription());

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not exist");
		}

		Note note = new Note();
		Date date = new Date();
		note.setUserId(userId);
		note.setCreateAt(date);
		note.setUpdateAt(date);
		note.setRemindAt(createNoteDTO.getRemindAt());

		if (createNoteDTO.isArchive()) {
			note.setArchive(true);
		} else {
			note.setArchive(false);
		}

		if (createNoteDTO.isPin()) {
			note.setPin(true);
		} else {
			note.setPin(false);
		}

		if (createNoteDTO.getColor() == "String" || createNoteDTO.getColor().trim().length() == 0
				|| createNoteDTO.getColor() == null) {
			note.setColor("white");
		} else {
			note.setColor(createNoteDTO.getColor());
		}

		note.setTitle(createNoteDTO.getTitle());
		note.setDescription(createNoteDTO.getDescription());

		List<String> labelList = createNoteDTO.getLadelList();

		if (labelList != null) {
			for (int i = 0; i < labelList.size(); i++) {
				Optional<Label> existedLabels = labelRepository.findByLabelNameAndUserId(labelList.get(i), userId);
				if (!existedLabels.isPresent()) {
					throw new LabelNotFoundException("User doesnot have labels");
				}
				List<LabelDTO> list = new ArrayList<>();
				if (labelList.get(i).trim().length() != 0 && labelList.get(i) != null) {
					LabelDTO labelDTO = new LabelDTO();
					labelDTO.setLabelId(existedLabels.get().getLabelId());
					labelDTO.setLabelName(existedLabels.get().getLabelName());
					list.add(labelDTO);
					note.setLabelList(list);
				}
			}

		}

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

			optionalNote.get().setTrash(false);
			noteRepository.save(optionalNote.get());
		}
	}

	@Override
	public void addReminder(String userId, String noteId, Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperlySetException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (remaindAt.before(new Date())) {
			throw new DateNotProperlySetException("set the date rather than todays date");
		}

		optionalNote.get().setRemindAt(remaindAt);

		noteRepository.save(optionalNote.get());
	}

	@Override
	public void removeReminder(String userId, String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		optionalNote.get().setRemindAt(null);

		noteRepository.save(optionalNote.get());
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
	public void setArchiveNotes(String userId, String noteId, boolean isArchive)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		if (optionalNote.get().isPin()) {
			optionalNote.get().setPin(false);
		}
		optionalNote.get().setArchive(isArchive);

		noteRepository.save(optionalNote.get());
	}

	@Override
	public void unArchiveNotes(String userId, String noteId, boolean isArchive)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		optionalNote.get().setArchive(isArchive);

		noteRepository.save(optionalNote.get());
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
	public void pinNote(String userId, String noteId, boolean isPin)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (optionalNote.get().isArchive()) {
			optionalNote.get().setArchive(false);
		}
		if (isPin) {
			optionalNote.get().setPin(true);
		} else {
			optionalNote.get().setPin(false);
		}
		noteRepository.save(optionalNote.get());

	}

	@Override
	public void unPinNote(String userId, String noteId,boolean isPin)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (isPin) {
			optionalNote.get().setPin(false);
			noteRepository.save(optionalNote.get());
		}
	}

	@Override
	public String createLabel(String userId, String labelName)
			throws UserNotFoundException, LabelNameAlreadyExistException {

		Optional<Label> optionalLabel = labelRepository.findByLabelName(labelName);

		if (optionalLabel.isPresent()) {
			throw new LabelNameAlreadyExistException("LabelName already in use");// change the name of exception
		}

		Label label = new Label();
		label.setLabelName(labelName);
		label.setUserId(userId);
		labelRepository.save(label);

		return labelName;
	}

	List<LabelDTO> labelList = new ArrayList<>();

	@Override
	public void addLabel(String userId, String labelId, String noteId) throws UserNotFoundException,
			NoteNotFoundException, UnAuthorizedException, LabelNameAlreadyExistException, LabelNotFoundException {

		LabelDTO labelDTO = null;
		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		Optional<Label> optionalLabel = labelRepository.findByLabelNameAndUserId(labelId, userId);
		if (!optionalLabel.isPresent()) {
			throw new LabelNotFoundException("Label not found");
		}

		labelDTO = new LabelDTO();
		labelDTO.setLabelId(optionalLabel.get().getLabelId());
		labelDTO.setLabelName(optionalLabel.get().getLabelName());
		labelList.add(labelDTO);
		optionalNote.get().setLabelList(labelList);
		noteRepository.save(optionalNote.get());

	}

	@Override
	public List<LabelDTO> getAllLabels(String userId) throws UserNotFoundException {

		List<LabelDTO> list = new ArrayList<>();
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Label> optionalLabels = labelRepository.findByUserId(userId);

		for (int i = 0; i < optionalLabels.size(); i++) {

			list.add(modelMapper.map(optionalLabels.get(i), LabelDTO.class));

		}

		return list;
	}

	@Override
	public void deleteLabel(String userId, String labelId)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		List<Note> optionalNote = noteRepository.findAllByUserId(userId);

		Optional<Label> optionalLabel = labelRepository.findByLabelNameAndUserId(labelId, userId);

		if (!optionalLabel.isPresent()) {
			throw new LabelNotFoundException("Label not found");
		}
		labelRepository.delete(optionalLabel.get());
		for (int i = 0; i < optionalNote.size(); i++) {

			for (int j = 0; j < optionalNote.get(i).getLabelList().size(); j++) {

				if (optionalNote.get(i).getLabelList().get(j).getLabelId().equals(optionalLabel.get().getLabelId())) {

					optionalNote.get(i).getLabelList().remove(j);
					noteRepository.save(optionalNote.get(i));

				}
			}
		}

	}

	@Override
	public void renameLabel(String userId, String labelId, String newLabelName)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		List<Note> optionalNotes = noteRepository.findAllByUserId(userId);

		Optional<Label> optionalLabel = labelRepository.findByLabelNameAndUserId(labelId, userId);
		if (!optionalLabel.isPresent()) {
			throw new LabelNotFoundException("Label not found");
		}
		optionalLabel.get().setLabelName(newLabelName);
		labelRepository.save(optionalLabel.get());

		for (int i = 0; i < optionalNotes.size(); i++) {

			for (int j = 0; j < optionalNotes.get(j).getLabelList().size(); j++) {

				if (optionalNotes.get(j).getLabelList().get(j).getLabelId().equals(optionalLabel.get().getLabelId())) {

					optionalNotes.get(i).getLabelList().get(j).setLabelName(newLabelName);
					noteRepository.save(optionalNotes.get(i));

				}
			}

		}

	}

	@Override
	public void removeNoteLabel(String userId, String noteId, String labelId) throws Exception {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);
		Optional<Label> optionalLabel = labelRepository.findByLabelNameAndUserId(labelId, userId);
		Note note = optionalNote.get();

		if (!optionalLabel.isPresent()) {
			throw new LabelNotFoundException("Label not found");
		}

		for (int i = 0; i < note.getLabelList().size(); i++) {

			if (note.getLabelList().get(i).getLabelId().equals(optionalLabel.get().getLabelId())) {

				note.getLabelList().remove(i);
				noteRepository.save(note);
				break;
			}
		}

	}

}
