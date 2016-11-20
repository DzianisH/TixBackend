package org.tix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by DzianisH on 20.11.2016.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Avatarisation required")
public class NotAvataredException extends NotAuthorisedException {
	public NotAvataredException(){}
	public NotAvataredException(String msg){
		super(msg);
	}
	public NotAvataredException(String msg, Throwable cause){
		super(msg, cause);
	}
	public NotAvataredException(Throwable cause){
		super(cause);
	}
}
