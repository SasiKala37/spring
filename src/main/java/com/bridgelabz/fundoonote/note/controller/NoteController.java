package com.bridgelabz.fundoonote.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.note.exceptions.NoteException;
import com.bridgelabz.fundoonote.note.model.NoteDTO;
import com.bridgelabz.fundoonote.note.services.NoteService;
import com.bridgelabz.fundoonote.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonote.user.model.ResponseDTO;
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> createNote(@RequestBody NoteDTO noteDTO,
			@RequestParam("token") String token) throws RegistrationException, NoteException {

		noteService.createNote(noteDTO, token);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("note created successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

	}
	@PostMapping("/delete")
	public  ResponseEntity<ResponseDTO> deleteNote(@RequestBody String noteId,@RequestParam("token") String token) throws RegistrationException, NoteException{
		noteService.deleteNote(token, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("deleted note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		
	}
	@PostMapping("/update")
	public ResponseEntity<ResponseDTO> updateNote(@RequestBody NoteDTO noteDTO,@RequestParam("token") String token,@RequestBody String noteId) throws RegistrationException, NoteException{
		noteService.updateNote(token, noteDTO, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("update note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		
	}
	@GetMapping("/read")
	public ResponseEntity<ResponseDTO> readNote(@RequestParam("tpken") String token) throws RegistrationException, NoteException{
		noteService.readNote(token);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("view notes successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}
}
