package com.indocosmo.pms.web.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public CustomException(String errorCode,String errorMessage){
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}
	
	public CustomException(String errorMessage){
		this.errorMessage=errorMessage;
	}
	
	public CustomException(){
		super("System Error Message");
	}
}
