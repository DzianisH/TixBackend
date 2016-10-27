package org.tix.responses;

import org.springframework.stereotype.Component;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Component
public abstract class Response<D> {
	private boolean success;
	private D data;

	public static SuccessResponse<?> create(Object data){
		return new SuccessResponse<>(data);
	}

	public static Response<?> create(Throwable error){
		// return ErrorResponse in production mode
		return new ThrowableResponse<>(error);
	}

	public Response(D data, boolean success){
		this.data = data;
		this.success = success;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
