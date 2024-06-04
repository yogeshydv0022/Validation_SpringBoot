package com.customException;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserErrorHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorType> handleNotFound(EmployeeNotFoundException enfe) {

		return new ResponseEntity<ErrorType>(
				new ErrorType(new Date(System.currentTimeMillis()).toString(),"404- NOT FOUND", enfe.getMessage()),
				HttpStatus.NOT_FOUND);
	}
}
