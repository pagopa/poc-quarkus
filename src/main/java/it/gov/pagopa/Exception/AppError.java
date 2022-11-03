package it.gov.pagopa.Exception;

import lombok.Getter;


@Getter
public enum AppError {

    ORGANIZATION_NOT_FOUND(404, "Organization not found", "Not found the Organization Fiscal Code %s"),
    ORGANIZATION_DUPLICATED(409, "Organization with the specified organization fiscal code already exists in the system", "Already exists an organization with Organization Fiscal Code %s"),
    INTERNAL_ERROR(500, "Error enrollment processing ", "Error enrollment processing  [Organization Fiscal Code = %s]"),
    UNKNOWN(520, null, null);


    public final int httpStatus;
    public final String title;
    public final String details;


    AppError(int httpStatus, String title, String details) {
        this.httpStatus = httpStatus;
        this.title = title;
        this.details = details;
    } 
}
