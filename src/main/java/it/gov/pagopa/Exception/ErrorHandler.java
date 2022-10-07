package it.gov.pagopa.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


import io.quarkus.logging.Log;

@Provider
public class ErrorHandler implements ExceptionMapper<AppException>{

    @Override
    public Response toResponse(AppException appException) {
        Log.info(appException.getCause());
        return Response.status(appException.httpStatus).entity(appException.getMessage()).build();
    }
}
