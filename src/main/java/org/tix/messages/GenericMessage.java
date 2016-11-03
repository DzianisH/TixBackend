package org.tix.messages;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
public class GenericMessage<D> {
	private D message;
	private boolean success;

	public GenericMessage(){}
	public GenericMessage(D message){
		this.message = message;
	}
	public GenericMessage(boolean success){
		this.success = success;
	}
	public GenericMessage(D message, boolean success){
		this.message = message;
		this.success = success;
	}

	public D getMessage() {
		return message;
	}

	public void setMessage(D message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
