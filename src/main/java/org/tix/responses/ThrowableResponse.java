package org.tix.responses;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
public class ThrowableResponse<D extends Throwable> extends Response<D> {
	public ThrowableResponse(D data) {
		super(data, false);
	}
}
