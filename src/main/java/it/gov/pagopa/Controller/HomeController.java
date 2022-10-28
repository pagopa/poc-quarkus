package it.gov.pagopa.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import it.gov.pagopa.Model.AppInfo;

@Produces(value = MediaType.APPLICATION_JSON)
@Path("")
public class HomeController {

    @ConfigProperty(name = "quarkus.application.name", defaultValue = "")
    private String name;

    @ConfigProperty(name = "quarkus.application.version", defaultValue = "")
    private String version;

    @ConfigProperty(name = "properties.environment")
    private String environment;
    
    @Operation(hidden = true)
    @GET
    @Path("")
    public Response home(){
        return Response.seeOther(UriBuilder.fromUri("/swagger").build()).build();
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AppInfo.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @GET
    @Path("/info")
    public Response healthCheck(){
        AppInfo info = AppInfo.builder()
                .name(name)
                .version(version)
                .environment(environment)
                .build();
        return Response.ok(info).build();
    }
}
