package com.example.tasklist.web.controller;

import java.util.List;import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tasklist.domain.exception.AccessDeniedException;
import com.example.tasklist.domain.exception.ExceptionBody;
import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.exception.ResourceNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
		return new ExceptionBody(e.getMessage());
	}
	
	@ExceptionHandler(ResourceMappingException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionBody handleResourceMapping(ResourceMappingException e) {
		return new ExceptionBody(e.getMessage());
	}
	
	
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleResourceMapping(IllegalStateException e) {
		return new ExceptionBody(e.getMessage());
	}
	
	@ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionBody handleAccessDenied() {
		return new ExceptionBody("Access denied");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleMethodArgumentNoValid(MethodArgumentNotValidException e) {
		ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		exceptionBody.setError(errors.stream().collect(Collectors.toMap(FieldError::getField , FieldError::getDefaultMessage)));
		return exceptionBody;
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleConstraintViolation(ConstraintViolationException e) {
		ExceptionBody exceptionBody = new ExceptionBody("Violation failed");
		exceptionBody.setError(e.getConstraintViolations().stream()
				.collect(Collectors.toMap(
						violation -> violation.getPropertyPath().toString(),
						violation -> violation.getPropertyPath().toString()
						)));
		return exceptionBody;
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleAuthentication(AuthenticationException e) {
		e.printStackTrace();
		ExceptionBody exceptionBody = new ExceptionBody("Authentication failed");
		return exceptionBody;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionBody handleException(Exception e) {
		e.printStackTrace();
		return new ExceptionBody("Internal error");
	}

}
