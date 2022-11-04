package com.pegasus.test.handler;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pegasus.test.dto.ApiResponseErrorDto;

@ControllerAdvice
public class RestControllerExceptionHandler {


	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResponseErrorDto> handleEntityNotFoundException(EntityNotFoundException exception){
		ApiResponseErrorDto dto = new ApiResponseErrorDto();
		dto.setCode(HttpStatus.NOT_FOUND.value());
		dto.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
		dto.setErrors(Arrays.asList(exception.getMessage()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(
						dto
						);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		ApiResponseErrorDto dto = new ApiResponseErrorDto();
		dto.setCode(HttpStatus.BAD_REQUEST.value());
		dto.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
		dto.setErrors(exception.getBindingResult().getFieldErrors().stream()
				.map( error -> error.getField()+" "+error.getDefaultMessage())
				.collect(Collectors.toList()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
						dto
						);

	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiResponseErrorDto> handleException(EntityNotFoundException exception){
		ApiResponseErrorDto dto = new ApiResponseErrorDto();
		dto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		dto.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		dto.setErrors(Arrays.asList(exception.getMessage()));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(
						dto
						);

	}
}
