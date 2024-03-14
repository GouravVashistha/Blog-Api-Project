package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	String resoureceName;
	String fieldName;
	long fieldValue;

	public ResourceNotFoundException(String resoureceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resoureceName, fieldName, fieldValue));
		this.resoureceName = resoureceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResoureceName() {
		return resoureceName;
	}

	public void setResoureceName(String resoureceName) {
		this.resoureceName = resoureceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}

}
