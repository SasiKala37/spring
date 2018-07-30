package com.bridgelabz.fundoonote.note.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.note.exceptions.LabelNameAlreadyExistException;
import com.bridgelabz.fundoonote.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.model.LabelDTO;
import com.bridgelabz.fundoonote.note.services.NoteService;
import com.bridgelabz.fundoonote.user.model.ResponseDTO;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private NoteService noteService;

	/**
	 * @param userId
	 * @param labelName
	 * @param request
	 * @return
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 * @throws LabelNameAlreadyExistException
	 */
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestAttribute("userId") String userId, @RequestParam String labelName)
			throws LabelNotFoundException, UserNotFoundException, LabelNameAlreadyExistException {

		String labelname = noteService.createLabel(userId, labelName);

		return new ResponseEntity<>(labelname, HttpStatus.OK);

	}

	/**
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getAll")
	public ResponseEntity<List<LabelDTO>> getAllLabels(@RequestAttribute("userId") String userId)
			throws UserNotFoundException {

		List<LabelDTO> labelDTOs = noteService.getAllLabels(userId);
		return new ResponseEntity<>(labelDTOs, HttpStatus.OK);
	}

	/**
	 * @param labelId
	 * @param userId
	 * @param request
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotFoundException
	 * @throws UnAuthorizedException
	 * @throws NoteNotFoundException
	 */
	@DeleteMapping("/delete/{labelName}")
	public ResponseEntity<ResponseDTO> deleteLabel(@PathVariable("labelId") String labelId,
			@RequestAttribute("userId") String userId)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException, NoteNotFoundException {

		noteService.deleteLabel(userId, labelId);

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
	 * @throws LabelNameAlreadyExistException
	 * @throws LabelNotFoundException
	 */
	@PutMapping("/add")
	public ResponseEntity<ResponseDTO> addLabel(@RequestParam String labelId, @RequestParam String noteId,
			@RequestAttribute("userId") String userId) throws UserNotFoundException, NoteNotFoundException,
			UnAuthorizedException, LabelNameAlreadyExistException, LabelNotFoundException {

		noteService.addLabel(userId, labelId, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("add the label successfully");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelId
	 * @param newLabelName
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/rename")
	public ResponseEntity<ResponseDTO> renameLabel(@RequestParam String labelId, @RequestParam String newLabelName,
			@RequestAttribute("userId") String userId)
			throws UserNotFoundException, LabelNotFoundException, UnAuthorizedException {

		noteService.renameLabel(userId, labelId, newLabelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("rename label successfully");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param labelId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/removenote")
	public ResponseEntity<ResponseDTO> removeNoteLabel(@RequestParam String noteId, @RequestParam String labelId,
			@RequestAttribute("userId") String userId) throws Exception {

		noteService.removeNoteLabel(userId, noteId, labelId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("remove note label successfully");
		responseDTO.setStatus(1);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
