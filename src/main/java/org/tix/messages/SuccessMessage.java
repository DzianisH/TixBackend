package org.tix.messages;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
public class SuccessMessage<D> extends GenericMessage<D> {
	public SuccessMessage(){
		super(true);
	}
	public SuccessMessage(D message){
		super(message, true);
	}
}
