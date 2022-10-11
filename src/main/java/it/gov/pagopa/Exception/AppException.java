package it.gov.pagopa.Exception;

public class AppException extends RuntimeException{
    
    String title;

    int httpStatus;

    public AppException(String title, int httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.title = title;
    }
}