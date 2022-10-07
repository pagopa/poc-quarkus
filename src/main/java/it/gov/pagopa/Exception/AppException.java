package it.gov.pagopa.Exception;

import java.io.Serializable;

public class AppException extends RuntimeException implements Serializable{
    
    String title;

    int httpStatus;

    public AppException(String title, int httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.title = title;
    }
}