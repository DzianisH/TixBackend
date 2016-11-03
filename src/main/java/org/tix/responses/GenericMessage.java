package org.tix.responses;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
public class GenericMessage<D> {
	private D message;

	public GenericMessage(){}
	public GenericMessage(D message){
		this.message = message;
	}

	public D getMessage() {
		return message;
	}

	public void setMessage(D message) {
		this.message = message;
	}
}
