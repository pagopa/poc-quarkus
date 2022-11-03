package it.gov.pagopa.Exception;

import javax.validation.constraints.NotNull;

import lombok.Getter;

import java.util.Formatter;

@Getter
public class AppException extends RuntimeException{
    
    String title;

    int httpStatus;

    public AppException(@NotNull AppError appError, Object... args) {
        super(formatDetails(appError, args));
        this.httpStatus = appError.httpStatus;
        this.title = appError.title;
    }

    private static String formatDetails(AppError appError, Object[] args) {
        return String.format(appError.details, args);
    }
}