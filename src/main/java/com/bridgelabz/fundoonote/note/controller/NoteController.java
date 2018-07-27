package com.bridgelabz.fundoonote.note.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.bridgelabz.fundoonote.note.services.NoteService;
import com.bridgelabz.fundoonote.user.model.ResponseDTO;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	/**
	 * create label
	 * 
	 * @param createNoteDTO
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 * @throws NoteCreationException
	 * @throws LabelNotFoundException
	 */
	@PostMapping("/createNote")
	public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO,
			@RequestAttribute("token") String userId) throws NoteNotFoundException, UnAuthorizedException,
			UserNotFoundException, NoteCreationException, LabelNotFoundException {

		NoteDTO noteDTO = noteService.createNote(createNoteDTO, userId);

		return new ResponseEntity<>(noteDTO, HttpStatus.CREATED);

	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<ResponseDTO> deleteNote(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.deleteNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("deleted note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param updateNoteDTO
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 * @throws NoteCreationException
	 */
	@PutMapping("/updateNote")
	public ResponseEntity<ResponseDTO> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO,
			@RequestAttribute("token") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException {

		noteService.updateNote(userId, updateNoteDTO);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("update note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param token
	 * @param request
	 * @param noteId
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PostMapping("/restoreNote/{noteId}")
	public ResponseEntity<ResponseDTO> restoreNote(@RequestAttribute("token") String userId,
			@PathVariable("noteId") String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.restoreNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("restore note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/readNotes")
	public ResponseEntity<List<NoteDTO>> readNote(@RequestAttribute("token") String userId)
			throws NoteNotFoundException, UserNotFoundException {

		List<NoteDTO> list = noteService.readNote(userId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/trashNote/{noteId}")
	public ResponseEntity<ResponseDTO> trashNote(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.trashNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("trash note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param token
	 * @param remaindAt
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 * @throws DateNotProperlySetException
	 */
	@PutMapping("/addremainder/{noteId}")
	public ResponseEntity<ResponseDTO> addRemainder(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId, @RequestBody Date remaindAt)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperlySetException {

		noteService.addRemainder(userId, noteId, remaindAt);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("remainder set successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/removeremainder/{noteId}")
	public ResponseEntity<ResponseDTO> removeRemainder(@PathVariable("noteId") String noteId,
			@RequestAttribute("userId") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.removeRemainder(userId, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("remainder removed");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getalltrashnotes")
	public ResponseEntity<List<NoteDTO>> getAllTrashNotes(@RequestAttribute("token") String userId)
			throws UserNotFoundException {

		List<NoteDTO> list = noteService.getAllTrashNotes(userId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/GetallarchiveNotes")
	public ResponseEntity<List<NoteDTO>> getAllArchiveNotes(@RequestAttribute("token") String userId)
			throws UserNotFoundException {

		List<NoteDTO> list = noteService.getAllArchiveNotes(userId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/setarchives/{noteId}")
	public ResponseEntity<ResponseDTO> setArchives(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.setArchiveNotes(userId, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("note added to archive");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param token
	 * @param color
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/changecolor/{noteId}")
	public ResponseEntity<ResponseDTO> changeColor(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId, @RequestParam String color)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.changeColor(userId, noteId, color);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("color changed");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/unarchivenote/{noteId}")
	public ResponseEntity<ResponseDTO> unArchiveNote(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.unArchiveNotes(userId, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("unarchive");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/pinnote/{noteId}")
	public ResponseEntity<ResponseDTO> setPin(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.pinNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("pinned note");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/unPinnote/{noteId}")
	public ResponseEntity<ResponseDTO> unPinNote(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.unPinNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("unpin the note");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @param labelName
	 * @param request
	 * @return
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 * @throws LabelNameAlreadyInUseException
	 */
	@PostMapping("/createLabel")
	public ResponseEntity<String> createLabel(@RequestAttribute("token") String userId, @RequestParam String labelName)
			throws LabelNotFoundException, UserNotFoundException, LabelNameAlreadyInUseException {

		String labelname = noteService.createLabel(userId, labelName);

		return new ResponseEntity<>(labelname, HttpStatus.OK);

	}

	/**
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getAllLabels")
	public ResponseEntity<List<LabelDTO>> getAllLabels(@RequestAttribute("token") String userId)
			throws UserNotFoundException {
		List<LabelDTO> labelDTOs = noteService.getAllLabels(userId);
		return new ResponseEntity<>(labelDTOs, HttpStatus.OK);
	}

	/**
	 * @param labelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotFoundException
	 * @throws UnAuthorizedException
	 * @throws NoteNotFoundException
	 */
	@DeleteMapping("/deleteLabel/{labelName}")
	public ResponseEntity<ResponseDTO> deleteLabel(@PathVariable("labelName") String labelName,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException, NoteNotFoundException {

		noteService.deleteLabel(userId, labelName);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("delete the label");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelDTO
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws LabelNameAlreadyInUseException
	 */
	@PostMapping("/addLabel")
	public ResponseEntity<ResponseDTO> addLabel(@RequestBody LabelDTO labelDTO, @RequestParam String noteId,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException, LabelNameAlreadyInUseException {

		noteService.addLabel(userId, labelDTO, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("add the label successfully");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PutMapping("/renameLabel")
	public ResponseEntity<ResponseDTO> renameLabel(@RequestParam String oldLabelName, @RequestParam String newLabelName,
			@RequestAttribute("token") String userId)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		noteService.renameLabel(userId, oldLabelName, newLabelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("add the label successfully");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
