package it.gov.pagopa.Controller;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class HomeControllerTest {

    @Test
    public void testGetFruits(){
        given()
        .when().get("/info")
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_JSON);
    } 
}
