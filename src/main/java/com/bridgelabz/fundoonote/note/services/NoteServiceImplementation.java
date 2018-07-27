package com.bridgelabz.fundoonote.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.exceptions.DateNotProperlySetException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNameAlreadyInUseException;
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
	public NoteDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws UserNotFoundException, NoteCreationException, NoteNotFoundException, UnAuthorizedException, LabelNotFoundException {

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
		
		note.setArchive(createNoteDTO.isArchive());
		
		note.setColor(createNoteDTO.getColor());
		note.setPin(createNoteDTO.isPin());
		note.setTitle(createNoteDTO.getTitle());
		note.setDescription(createNoteDTO.getDescription());
		
		List<String> labelList=createNoteDTO.getLadelList();
		
		if(labelList!=null) {
			for(int i=0;i<labelList.size();i++) {
				Optional<Label> existedLabels=labelRepository.findByLabelNameAndUserId(labelList.get(i), userId);
				if(!existedLabels.isPresent()) {
					throw new LabelNotFoundException("User doesnot have labels");
				}
				List<LabelDTO> list=new ArrayList<>();
				if(labelList.get(i).trim().length()!=0&&labelList.get(i)!=null){
					LabelDTO labelDTO=new LabelDTO();
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
			Note note = optionalNote.get();
			note.setTrash(false);

			noteRepository.save(note);

		}
	}

	@Override
	public void addRemainder(String userId, String noteId, Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperlySetException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (remaindAt.before(new Date())) {
			throw new DateNotProperlySetException("set the date rather than todays date");
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
	public void pinNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (!optionalNote.get().isPin()) {
			optionalNote.get().setPin(true);
			noteRepository.save(optionalNote.get());
		}

	}

	@Override
	public void unPinNote(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		if (optionalNote.get().isPin()) {
			optionalNote.get().setPin(true);
			noteRepository.save(optionalNote.get());
		}
	}

	@Override
	public String createLabel(String userId, String labelName)
			throws UserNotFoundException, LabelNameAlreadyInUseException {

		Optional<Label> optionalLabel = labelRepository.findByLabelName(labelName);

		if (optionalLabel.isPresent()) {
			throw new LabelNameAlreadyInUseException("LabelName already in use");
		}

		Label label = new Label();
		label.setLabelName(labelName);
		label.setUserId(userId);
		labelRepository.save(label);

		return labelName;
	}

	@Override
	public void addLabel(String userId, LabelDTO labelDTO, String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException, LabelNameAlreadyInUseException {
		//Label label=modelMapper.map(labelDTO, Label.class);
		List<LabelDTO> labelList = new ArrayList<>();
		Optional<Note> optionalNote = checkUserNote(userId, noteId);

		Optional<Label> optionalLabel = labelRepository.findByLabelNameAndUserId(labelDTO.getLabelName(), userId);

		//for (int i = 0; i < optionalLabel.size(); i++) {

			if (optionalLabel.get().getLabelName().equals(labelDTO.getLabelName())) {

				optionalLabel.get().setLabelId(labelDTO.getLabelId());
				optionalLabel.get().setUserId(userId);
				labelRepository.save(optionalLabel.get());

				labelList.add(labelDTO);
				optionalNote.get().setLabelList(labelList);
				noteRepository.save(optionalNote.get());

			//}
		}

	}

	@Override
	public List<LabelDTO> getAllLabels(String userId) throws UserNotFoundException {
		List<LabelDTO> labelList = new ArrayList<>();
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Label> optionalLabel = labelRepository.findByUserId(userId);

		for (int i = 0; i < optionalLabel.size(); i++) {

			labelList.add(modelMapper.map(optionalLabel.get(i), LabelDTO.class));

		}

		return labelList;
	}

	@Override
	public void deleteLabel(String userId, String labelName)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Note> optionalNote = noteRepository.findAllByQuery(userId,labelName);

		Optional<Label> optionalLabelList = labelRepository.findByLabelNameAndUserId(labelName, userId);

		//for (int i = 0; i < optionalLabelList.size(); i++) {

			for (int j = 0; j < optionalNote.get(j).getLabelList().size(); j++) {
				// match the given userId with note userId
				if (optionalNote.get(j).getUserId().equals(optionalUser.get().getId())) {
					// match the given labelListId with noteList labelId
					if (optionalNote.get(j).getLabelList().get(j).getLabelId()
							.equals(optionalLabelList.get().getLabelId())) {

						noteRepository.deleteById(optionalNote.get(j).getLabelList().get(j).getLabelId());
						labelRepository.delete(optionalLabelList.get());
					}
				}
			}
		//}

	}

	@Override
	public void renameLabel(String userId, String oldLabelName, String newLabelName)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		List<Note> optionalNote = noteRepository.findByUserIdAndLabelListInLabelName(userId, oldLabelName);

		Optional<Label> optionalLabelList = labelRepository.findByLabelNameAndUserId(oldLabelName, userId);

		//for (int i = 0; i < optionalLabelList.size(); i++) {

			for (int j = 0; j < optionalNote.get(j).getLabelList().size(); j++) {
				// match the given userId with note userId & match the given labelListId with
				// noteList labelId

				if (optionalNote.get(j).getUserId().equals(userId) && optionalNote.get(j).getLabelList().get(j)
						.getLabelId().equals(optionalLabelList.get().getLabelId())) {

					optionalNote.get(j).getLabelList().get(j).setLabelName(newLabelName);
					noteRepository.save(optionalNote.get(j));

					optionalLabelList.get().setLabelName(newLabelName);
					labelRepository.save(optionalLabelList.get());

				}
			}
		//}

	}

}
