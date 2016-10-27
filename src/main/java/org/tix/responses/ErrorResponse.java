package org.tix.responses;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
public class ErrorResponse extends  Response<String> {
	public ErrorResponse(String message) {
		super(message, false);
	}
}
