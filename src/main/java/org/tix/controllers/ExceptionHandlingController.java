package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;
import org.tix.messages.GenericMessage;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
@ControllerAdvice
public class ExceptionHandlingController {
	private static final Logger LOG = Logger.getLogger(ExceptionHandlingController.class);

	@ExceptionHandler(NoSuchBeanException.class)
	public ResponseEntity<?> handleNotFound(NoSuchBeanException exp){
		return new ResponseEntity<>(
				new GenericMessage<>(exp.getMessage()),
				HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler(InvalidBeanException.class)
	public ResponseEntity<?> handleInvalidRequest(InvalidBeanException exp){
		return new ResponseEntity<>(
				new GenericMessage<>(exp.getMessage()),
				HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(BeanAlreadyInUse.class)
	public ResponseEntity<?> handlePermissionsDenied(BeanAlreadyInUse exp){
		return new ResponseEntity<>(
				new GenericMessage<>(exp.getMessage()),
				HttpStatus.LOCKED
		);
	}

	@ExceptionHandler({NotAuthorisedException.class, PermissionDeniedException.class})
	public ResponseEntity<?> handle(RuntimeException exp){
		return new ResponseEntity<>(
				new GenericMessage<>(exp.getMessage()),
				HttpStatus.FORBIDDEN
		);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<GenericMessage<String>> handleGeneric(Throwable err){
		LOG.error("Unexpected error occurred: " + err.getLocalizedMessage(), err);

		return new ResponseEntity<>(
				new GenericMessage<>(err.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
}
