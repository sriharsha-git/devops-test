package com.stacksimplify.restservices.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalRestControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final CustomErrorDetails userNotFound(UserNameNotFoundException ex) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "NOT_FOUND", ex.getMessage());
		return customErrorDetails;
	}

	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final CustomErrorDetails orderNotFound(OrderNotFoundException ex) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "NOT_FOUND", ex.getMessage());
		return customErrorDetails;
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final CustomErrorDetails badCredentials(InvalidCredentialsException ex) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "UNAUTHORIZED", ex.getMessage());
		return customErrorDetails;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "BAD_REQUEST", ex.getMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errorMessages = new ArrayList<>();
		BindingResult bindingResult = ex.getBindingResult();
		List<ObjectError> errors = bindingResult.getAllErrors();

		for (ObjectError error : errors) {
			String message = error.getDefaultMessage();
			errorMessages.add(message);
		}

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "BAD_REQUEST", errorMessages.get(0));

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "METHOD_NOT_ALLOWED",
				ex.getMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);

	}

}
