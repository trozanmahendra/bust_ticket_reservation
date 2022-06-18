package com.mgWork.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mgWork.dto.ErrorObject;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();

		body.put("statusCode", HttpStatus.BAD_REQUEST.value());

		List<String> errors = ex
								.getBindingResult()
								.getFieldErrors()
								.stream().map(x -> x.getDefaultMessage())
								.collect(Collectors.toList());

		body.put("messages", errors);

		body.put("timestamp", new Date());

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleGeneralException(Exception ex, WebRequest request) {
		
		ErrorObject errorObject = new ErrorObject();
		
		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		errorObject.setMessage(ex.getMessage());
		
		errorObject.setTimestamp(new Date());
		
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
