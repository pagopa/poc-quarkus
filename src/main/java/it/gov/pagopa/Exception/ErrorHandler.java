package it.gov.pagopa.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


import io.quarkus.logging.Log;
import it.gov.pagopa.Model.ProblemJson;

@Provider
public class ErrorHandler implements ExceptionMapper<AppException>{

    @Override
    public Response toResponse(AppException appException) {
        var errorResponse = ProblemJson.builder()
        .status(appException.getHttpStatus())
        .title(appException.getTitle())
        .detail(appException.getMessage())
        .build();
        return Response.status(appException.httpStatus).entity(errorResponse).build();
    }
}
