package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by DzianisH on 04.11.2016.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Permissions denied")
public class PermissionDeniedException extends Exception {
	public PermissionDeniedException(){}
	public PermissionDeniedException(String msg){
		super(msg);
	}
	public PermissionDeniedException(String msg, Throwable cause){
		super(msg, cause);
	}
	public PermissionDeniedException(Throwable cause){
		super(cause);
	}
}
