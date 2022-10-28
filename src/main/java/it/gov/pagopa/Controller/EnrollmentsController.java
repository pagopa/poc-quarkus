package it.gov.pagopa.Controller;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Service.EnrollmentsService;

@Produces(value = MediaType.APPLICATION_JSON)
@Path("/organizations")
public class EnrollmentsController {

    @Inject 
    EnrollmentsService enrollmentsService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = OrganizationEntity.class))),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @ApiResponse(responseCode = "401", description = "Unauthourized", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Path("/{organizationFiscalCode}")
    @POST
    public Response createOrganization(@PathParam("organizationFiscalCode")String organizationFiscalCode){
        return Response.status(201).entity(enrollmentsService.createOrganization(organizationFiscalCode)).build();
    }
}
