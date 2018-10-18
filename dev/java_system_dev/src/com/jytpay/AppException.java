package com.jytpay;

public class AppException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode = "";
	
	private String errorMessage = "";
	
	public AppException(String errorCode){
		super(errorCode);
		this.errorCode = errorCode;
	}
	
	public AppException(String errorCode,String errorMessage){
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode(){
		return errorCode;
	}
	
	public AppException() {
        super();
    }
 
    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }
    
    public AppException(String errorCode,String errorMessage, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
 
    public AppException(Throwable cause) {
        super(cause);
    }

	public String getErrorMessage() {
		return errorMessage;
	}
    
    
	
}
