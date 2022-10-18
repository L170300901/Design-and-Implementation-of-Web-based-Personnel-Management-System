package kr.co.yooooon.common.exception;

@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {
	public DataAccessException(){
		super();
	}
	
	public DataAccessException(String message){
		super(message);
	}
	public DataAccessException(Throwable cause){
		super(cause);
	}
	public DataAccessException(String message, Throwable cause){
		super(message, cause);
	}
}