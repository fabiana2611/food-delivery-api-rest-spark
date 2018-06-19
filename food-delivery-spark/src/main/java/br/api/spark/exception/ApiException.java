package br.api.spark.exception;

public class ApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final int statusCode;

    public ApiException (int statusCode, String msg){
        super(msg); //see explanation below
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
