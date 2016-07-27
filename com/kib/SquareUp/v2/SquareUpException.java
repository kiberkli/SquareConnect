package com.kib.SquareUp.v2;

import java.util.List;

public class SquareUpException extends Exception {
	
	private List<Error> errors = null;
	
	public SquareUpException(List<Error> squareErrors) {
		errors = squareErrors;
	}

	public String getMessage() {
		return errors.get(0).detail;
	}

	public String getCategory() {
		return errors.get(0).category;
	}

	public String getCode() {
		return errors.get(0).code;
	}

	public String getDetail() {
		return errors.get(0).detail;
	}
		
	public String getField() {
		return errors.get(0).detail;
	}
	
	public List<Error> getErrors() {
		return errors;
	}
	
}
