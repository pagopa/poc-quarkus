package it.gov.pagopa.Controller;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


import it.gov.pagopa.Model.OrganizationModelResponse;
import it.gov.pagopa.Service.EnrollmentsService;
import it.gov.pagopa.Mapper.OrganizationMapper;

@Produces(value = MediaType.APPLICATION_JSON)
@Path("/organizations")
public class EnrollmentsController {

    @Inject 
    EnrollmentsService enrollmentsService;

    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = OrganizationModelResponse.class))),
        @APIResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "401", description = "Unauthourized", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Path("/{organizationFiscalCode}")
    @POST
    public Response createOrganization(@PathParam("organizationFiscalCode")String organizationFiscalCode){
        return Response.status(201).entity(enrollmentsService.createOrganization(organizationFiscalCode)).build();
    }

    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Request deleted.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(name = "StringResponse", implementation = OrganizationModelResponse.class))),
        @APIResponse(responseCode = "401", description = "Unauthourized.", content = @Content(schema = @Schema())),
        @APIResponse(responseCode = "404", description = "Not found the creditor institution.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Path("/{organizationFiscalCode}")
    @DELETE
    public Response removeOrganization(@PathParam("organizationFiscalCode")String organizationFiscalCode){
        return Response.ok("The enrollment to reporting service for the organization \"" + enrollmentsService.removeOrganization(organizationFiscalCode) + "\" was successfully removed").build();
    }

    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Obtained single enrollment.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(name = "OrganizationModelResponse", implementation = OrganizationModelResponse.class))),
        @APIResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema())),
        @APIResponse(responseCode = "404", description = "Not found the enroll service.", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Path("/{organizationFiscalCode}")
    @GET
    public Response getOrganization(@PathParam("organizationFiscalCode")String organizationFiscalCode){
        return Response.ok(enrollmentsService.getOrganization(organizationFiscalCode)).build();
    }
    
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Obtained all enrollments.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = OrganizationModelResponse.class))),
        @APIResponse(responseCode = "401", description = "Wrong or missing function key.", content = @Content(schema = @Schema())),
        @APIResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @GET
    public Response getOrganizations(){
        return Response.ok(enrollmentsService.getOrganizations()).build();
    }
}
