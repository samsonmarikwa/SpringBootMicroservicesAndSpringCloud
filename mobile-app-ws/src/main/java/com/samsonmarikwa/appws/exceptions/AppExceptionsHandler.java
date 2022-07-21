package com.samsonmarikwa.appws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.samsonmarikwa.appws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler {
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) errorMessageDescription = ex.toString();
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
		
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) errorMessageDescription = ex.toString();
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest request) {
		
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) errorMessageDescription = ex.toString();
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
