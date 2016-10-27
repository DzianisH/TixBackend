package org.tix.responses;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
public class SuccessResponse<D> extends Response<D> {
	public SuccessResponse(D data) {
		super(data, true);
	}
}
