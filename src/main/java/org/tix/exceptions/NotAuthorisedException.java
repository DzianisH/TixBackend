package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by DzianisH on 04.11.2016.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Authorisation required")
public class NotAuthorisedException extends PermissionDeniedException {
	public NotAuthorisedException(){}
	public NotAuthorisedException(String msg){
		super(msg);
	}
	public NotAuthorisedException(String msg, Throwable cause){
		super(msg, cause);
	}
	public NotAuthorisedException(Throwable cause){
		super(cause);
	}
}
