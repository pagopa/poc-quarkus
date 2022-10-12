package it.gov.pagopa.Controller;


import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class EnrollmentControllerTest {
    
    @Test
    public void testCreateOrganization(){
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when().post("/organizations/mockOrganizationFiscalCode")
        .then()
        .contentType(MediaType.APPLICATION_JSON)
        .statusCode(201);
    } 
}
