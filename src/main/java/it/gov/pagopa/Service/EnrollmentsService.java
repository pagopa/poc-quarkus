package it.gov.pagopa.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.MalformedInputException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

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
            new OrganizationEntity(organizationFiscalCode, LocalDateTime.now()).persist();
        else
            throw new AppException("Conflict", 409, "Fiscal code already present");
        return new OrganizationModelResponse(organizationFiscalCode, LocalDateTime.now());
    }

    @Transactional
    public String removeOrganization(String organizationFiscalCode){
        if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isPresent())
            OrganizationEntity.delete("organizationFiscalCode", organizationFiscalCode);
        else
            throw new AppException("Not found", 404, "Organization fiscal code \"" + organizationFiscalCode + "\" does not exists");
        return organizationFiscalCode;
    }

    @Transactional
    public OrganizationEntity getOrganization(String organizationFiscalCode){
        if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isPresent())
            return OrganizationEntity.find("organizationFiscalCode", organizationFiscalCode).singleResult();
        else
            throw new AppException("Not found", 404, "Organization fiscal code does not exists");
    }

    @Transactional
    public List<OrganizationModelResponse> getOrganizations(){
        List<OrganizationEntity> orgs = OrganizationEntity.listAll();
        return orgs.stream().map(o -> new OrganizationModelResponse(o.getOrganizationFiscalCode(), o.getOrganizationOnboardingDate())).collect(Collectors.toList());
    }
}
