package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@ResponseStatus(value = HttpStatus.LOCKED, reason = "This entity already used by another user")
public class BeanAlreadyInUse extends RuntimeException {
	public BeanAlreadyInUse(){}
	public BeanAlreadyInUse(String msg){
		super(msg);
	}
	public BeanAlreadyInUse(String msg, Throwable cause){
		super(msg, cause);
	}
	public BeanAlreadyInUse(Throwable cause){
		super(cause);
	}
}
