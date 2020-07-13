package com.application.demo.exceptions;

public class ProductAlreadyInUseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAlreadyInUseException(String errorMessage) {
			super(errorMessage);
	}

}
