package com.bridgelabz.fundoonote.note.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bridgelabz.fundoonote.note.exceptions.DateNotProperSetException;
import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;
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
	 * @param createNoteDTO
	 * @param token
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 * @throws NoteCreationException
	 */
	@PostMapping("/create")
	public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO,
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException {

		NoteDTO noteDTO = noteService.createNote(createNoteDTO, (String) request.getAttribute("token"));

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
	@DeleteMapping("/delete/{noteId}")
	public ResponseEntity<ResponseDTO> deleteNote(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		String userId = (String) request.getAttribute("token");
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
	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO,
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException {

		String userId = (String) request.getAttribute("token");
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
	@PostMapping("/restore/{noteId}")
	public ResponseEntity<ResponseDTO> restoreNote(@RequestAttribute("token") String token, HttpServletRequest request,
			@PathVariable("noteId") String noteId)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		String userId = (String) request.getAttribute("token");
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
	@GetMapping("/read")
	public ResponseEntity<List<NoteDTO>> readNote(@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UserNotFoundException {

		String userId = (String) request.getAttribute("token");
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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		String userId = (String) request.getAttribute("token");
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
	 * @throws DateNotProperSetException
	 */
	@PutMapping("/addremainder/{noteId}")
	public ResponseEntity<ResponseDTO> addRemainder(@PathVariable("noteId") String noteId,
			@RequestAttribute("token") String token, @RequestBody Date remaindAt, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException, DateNotProperSetException {

		String userId = (String) request.getAttribute("token");
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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		String userId = (String) request.getAttribute("token");
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
	public ResponseEntity<List<NoteDTO>> getAllTrashNotes(@RequestAttribute("token") String token,
			HttpServletRequest request) throws UserNotFoundException {

		List<NoteDTO> list = noteService.getAllTrashNotes((String) request.getAttribute("token"));

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/Getallarchives")
	public ResponseEntity<List<NoteDTO>> getAllArchiveNotes(@RequestAttribute("token") String token,
			HttpServletRequest request) throws UserNotFoundException {

		List<NoteDTO> list = noteService.getAllArchiveNotes((String) request.getAttribute("token"));

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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.setArchiveNotes((String) request.getAttribute("token"), noteId);

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
			@RequestAttribute("token") String token, @RequestParam String color, HttpServletRequest request)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.changeColor((String) request.getAttribute("token"), noteId, color);

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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.unArchiveNotes((String) request.getAttribute("token"), noteId);

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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.pinNote((String) request.getAttribute("token"), noteId);
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
			@RequestAttribute("token") String token, HttpServletRequest request)
			throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.unPinNote((String) request.getAttribute("token"), noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("unpin the note");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	

}
