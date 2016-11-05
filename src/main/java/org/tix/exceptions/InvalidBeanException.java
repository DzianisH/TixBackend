package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request bean validation failed")
public class InvalidBeanException extends Exception {
	public InvalidBeanException(){}
	public InvalidBeanException(String msg){
		super(msg);
	}
	public InvalidBeanException(String msg, Throwable cause){
		super(msg, cause);
	}
	public InvalidBeanException(Throwable cause){
		super(cause);
	}
}
