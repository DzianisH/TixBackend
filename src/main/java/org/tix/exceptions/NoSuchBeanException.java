package org.tix.exceptions;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
public class NoSuchBeanException extends RuntimeException {
	public NoSuchBeanException(){}
	public NoSuchBeanException(String msg){
		super(msg);
	}
}
