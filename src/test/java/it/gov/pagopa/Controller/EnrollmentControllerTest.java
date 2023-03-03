package it.gov.pagopa.Controller;

import it.gov.pagopa.Exception.AppError;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Model.OrganizationModelResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.InjectMocks;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import it.gov.pagopa.Service.EnrollmentsService;
import it.gov.pagopa.Util.TestUtil;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class EnrollmentControllerTest {
    
    @InjectMock
    private EnrollmentsService enrollmentsService;

    @Test
    public void testCreateOrganization201(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().post("/organizations/mockOrganizationFiscalCode")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(201);
    }

    @Test
    @DisplayName("Create organization 409")
    public void testCreateOrganization409(){
        when(enrollmentsService.createOrganization("mockOrganizationError")).thenThrow(new AppException(AppError.ORGANIZATION_DUPLICATED, "mockOrganizationError"));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().post("/organizations/mockOrganizationError")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(409);
    }

    @Test
    public void testGetOrganization200(){
        given()
                .when().get("/organizations/mockOrganizationFiscalCode")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200);
    }

    @Test
    public void testGetOrganization404(){
        when(enrollmentsService.getOrganization("mockOrganizationError")).thenThrow(new AppException(AppError.ORGANIZATION_NOT_FOUND, "mockOrganizationError"));
        given()
                .when().get("/organizations/mockOrganizationError")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(404);
    }

    @Test
    public void testGetOrganizations(){
        given()
                .when().get("/organizations/mockOrganizationFiscalCode")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200);
    }

    @Test
    void testDeleteOrganization200() throws Exception {
        given()
                .when().delete("/organizations/mockOrganizationFiscalCode")
                .then()
                .statusCode(200)
                .body(containsString("was successfully removed"));

    }
}
