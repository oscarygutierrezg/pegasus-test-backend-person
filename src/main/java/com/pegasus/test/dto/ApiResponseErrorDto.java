package com.pegasus.test.dto;

import java.util.List;

public class ApiResponseErrorDto {
	
	private int code;
	private String message;
	private List<String> errors;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}

