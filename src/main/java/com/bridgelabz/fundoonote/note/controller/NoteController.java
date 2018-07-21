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
	

	@PostMapping("/create")
	public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO,
			@RequestAttribute("token") String token,HttpServletRequest request) throws  NoteNotFoundException,  UnAuthorizedException, UserNotFoundException, NoteCreationException {
		
		String userId=(String) request.getAttribute("token");
		System.out.println("controller userId:"+userId);
		NoteDTO noteDTO=noteService.createNote(createNoteDTO, userId);
		
		return new ResponseEntity<>(noteDTO, HttpStatus.CREATED);

	}
	
	@DeleteMapping("/delete/{noteId}")
	public  ResponseEntity<ResponseDTO> deleteNote(@PathVariable("noteId") String noteId,@RequestAttribute("token") String token,HttpServletRequest request) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException{
		
		String userId=(String) request.getAttribute("token");
		noteService.deleteNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("deleted note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO,@RequestAttribute("token") String token,HttpServletRequest request) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException, NoteCreationException{
	
		String userId=(String) request.getAttribute("token");
		noteService.updateNote(userId, updateNoteDTO);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("update note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<NoteDTO>> readNote(@RequestAttribute("token") String token,HttpServletRequest request) throws NoteNotFoundException, UserNotFoundException{
		
		String userId=(String) request.getAttribute("token");
		List<NoteDTO> list=noteService.readNote(userId);
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/trashNote/{noteId}")
	public ResponseEntity<ResponseDTO> trashNote(@PathVariable("noteId") String noteId,@RequestParam("token") String token,HttpServletRequest request) throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException{
		
		String userId=(String) request.getAttribute("token");
		noteService.trashNote(userId, noteId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("trash note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	
	}
	
	@PutMapping("/addremainder/{noteId}")
	public ResponseEntity<ResponseDTO> addRemainder(@PathVariable("noteId") String noteId,@RequestParam("token")String token,@RequestParam("date") Date remaindAt,HttpServletRequest request) throws  NoteNotFoundException, UnAuthorizedException, UserNotFoundException{
		
		String userId=(String)request.getAttribute("token");
		noteService.addRemainder(userId, noteId, remaindAt);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("trash note successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}
	
}
