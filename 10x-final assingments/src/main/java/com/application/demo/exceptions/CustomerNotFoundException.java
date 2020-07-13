package com.application.demo.exceptions;

public class CustomerNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3085946501832501248L;

	public CustomerNotFoundException(String errorMessage) {
			super(errorMessage);
	}
}
