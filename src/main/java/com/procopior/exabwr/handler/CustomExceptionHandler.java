package com.procopior.exabwr.handler;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.procopior.exabwr.dto.ResponseErrorDTO;
import com.procopior.exabwr.exception.UserException;

@RestControllerAdvice
public class CustomExceptionHandler {

	private final Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ResponseErrorDTO> userException(final UserException ex) {
		LOGGER.error("Handler exception {}", ex);
		final ResponseErrorDTO errorDTO = new ResponseErrorDTO();
		errorDTO.setCode(HttpStatus.NOT_FOUND.toString());
		errorDTO.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseErrorDTO> exception(final Exception ex) {
		LOGGER.error("Handler exception {}", ex);
		final ResponseErrorDTO errorDTO = new ResponseErrorDTO();
		errorDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorDTO.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
		final ResponseErrorDTO errorDTO = new ResponseErrorDTO();
		final String errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", ", "(", ")"));
		errorDTO.setCode(HttpStatus.BAD_REQUEST.toString());
		errorDTO.setMessage(errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
	}
}
