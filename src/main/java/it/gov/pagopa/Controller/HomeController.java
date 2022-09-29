package it.gov.pagopa.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.gov.pagopa.Model.AppInfo;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("")
public class HomeController {

    @ConfigProperty(name = "quarkus.application.name", defaultValue = "")
    private String name;

    @ConfigProperty(name = "quarkus.application.version", defaultValue = "")
    private String version;

    @ConfigProperty(name = "properties.environment")
    private String environment;
    
    @GET
    @Path("/info")
    public Response healthCheck(){
        AppInfo info = AppInfo.builder()
                .name(name)
                .version(version)
                .environment(environment)
                .build();

        return Response.ok(info.toString()).build();
    }
}
