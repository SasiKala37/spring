package com.bridgelabz.fundoonote.note.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonote.user.model.ResponseDTO;

@ControllerAdvice
public class GlobalNoteExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalNoteExceptionHandler.class);

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleNoteNotFoundException(NoteNotFoundException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ResponseDTO> handleUnAuthorizedException(UnAuthorizedException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleUserNotFoundException(UserNotFoundException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NoteCreationException.class)
	public ResponseEntity<ResponseDTO> handleNoteCreationException(NoteCreationException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleLabelNotFoundException(LabelNotFoundException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-2);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(LabelNameAlreadyInUseException.class)
	public ResponseEntity<ResponseDTO> handleLabelNameAlreadyInUseException(LabelNameAlreadyInUseException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-2);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(DateNotProperlySetException.class)
	public ResponseEntity<ResponseDTO> handleDateNotProperlySetException(DateNotProperlySetException exception) {

		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage(exception.getMessage());
		response.setStatus(-2);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	/**
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> handleException(Exception exception, HttpServletRequest request) {

		logger.error("Error occured for: " + exception.getMessage(), exception);
		ResponseDTO response = new ResponseDTO();
		response.setMessage("Something went wrong");
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
