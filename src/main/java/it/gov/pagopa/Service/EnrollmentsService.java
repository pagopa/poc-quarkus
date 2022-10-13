package it.gov.pagopa.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.MalformedInputException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

import org.jboss.resteasy.reactive.server.multipart.MultipartPartReadingException;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Model.OrganizationModelResponse;

@ApplicationScoped
public class EnrollmentsService {
    
    @Transactional
    public OrganizationModelResponse createOrganization(String organizationFiscalCode){
        if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isEmpty())
        {
            OrganizationEntity orgEnt = new OrganizationEntity(organizationFiscalCode, LocalDateTime.now());
            orgEnt.persist();
        }
        else
        {
            throw new AppException("Conflict", 409, "Fiscal code already present");
        }
        return new OrganizationModelResponse(organizationFiscalCode, LocalDateTime.now());
    }
}
