package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not such entity")
public class NoSuchBeanException extends RuntimeException {
	public NoSuchBeanException(){}
	public NoSuchBeanException(String msg){
		super(msg);
	}
}
