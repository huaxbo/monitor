package com.hxb;

public class ACException extends Exception {
	private static final long serialVersionUID = 157680702136927039L;
	private String message;
	private Exception e;

	public ACException() {
	}

	public ACException(Exception e) {
		this.e = e;
	}

	public ACException(String message) {
		this.message = message;
	}

	public ACException(String message, Exception e) {
		this.message = message;
		this.e = e;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void printStackTrace() {
		if (this.e != null)
			this.e.printStackTrace();
	}
}