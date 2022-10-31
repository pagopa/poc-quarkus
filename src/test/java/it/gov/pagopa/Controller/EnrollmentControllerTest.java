package it.gov.pagopa.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import it.gov.pagopa.Service.EnrollmentsService;
import it.gov.pagopa.Util.TestUtil;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class EnrollmentControllerTest {

    @InjectMock
    private EnrollmentsService enrollmentsService;

    @BeforeEach
    void setUp() {
        when(enrollmentsService.getOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.createOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.getOrganizations()).thenReturn(TestUtil.getMockOrganizationEntityList());
    }
    
    @Test
    public void testCreateOrganization(){
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when().post("/organizations/mockOrganizationFiscalCode")
        .then()
        .contentType(MediaType.APPLICATION_JSON)
        .statusCode(201);
    } 

    @Test
    public void testGetOrganization(){
        given()
        .when().get("/organizations/mockOrganizationFiscalCode")
        .then()
        .contentType(MediaType.APPLICATION_JSON)
        .statusCode(200);
    } 

    @Test
    public void testGetOrganizations(){
        given()
        .when().get("/organizations/mockOrganizationFiscalCode")
        .then()
        .contentType(MediaType.APPLICATION_JSON)
        .statusCode(200);
    }
}
