package com.pegasus.test.handler;

import java.util.Arrays;
import java.util.List;
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
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(
						createApiResponseErrorDto(HttpStatus.NOT_FOUND, Arrays.asList(exception.getMessage()))
						);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
						createApiResponseErrorDto(HttpStatus.BAD_REQUEST, exception.getBindingResult().getFieldErrors().stream()
								.map( error -> error.getField()+" "+error.getDefaultMessage())
								.collect(Collectors.toList()))
						);

	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiResponseErrorDto> handleException(EntityNotFoundException exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(
						createApiResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(exception.getMessage()))
						);

	}

	private ApiResponseErrorDto createApiResponseErrorDto(HttpStatus httpStatus,List<String> errors) {
		return ApiResponseErrorDto.builder()
				.code(httpStatus.value())
				.message(httpStatus.getReasonPhrase())
				.errors(errors)
				.build();
	}
}
