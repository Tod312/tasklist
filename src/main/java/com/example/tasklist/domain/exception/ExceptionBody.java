package com.example.tasklist.domain.exception;

import java.util.Map;

public class ExceptionBody {
	
	private String message;
	private Map<String, String> errors;
	
	public ExceptionBody(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getError() {
		return errors;
	}

	public void setError(Map<String, String> errors) {
		this.errors = errors;
	}
	
	
	
}
