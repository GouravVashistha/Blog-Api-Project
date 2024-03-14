package com.blog.exceptions;

public class DetailsNotFoundException  extends Exception{

	public DetailsNotFoundException() {
		super();
		
	}

	public DetailsNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public DetailsNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public DetailsNotFoundException(String message) {
		super(message);

	}

	public DetailsNotFoundException(Throwable cause) {
		super(cause);

	}

}
