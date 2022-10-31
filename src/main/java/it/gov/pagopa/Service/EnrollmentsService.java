package it.gov.pagopa.Service;


import java.time.LocalDateTime;
import java.util.List;

import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Mapper.OrganizationMapper;
import it.gov.pagopa.Model.OrganizationModelResponse;

@ApplicationScoped
public class EnrollmentsService {

    @Inject
    OrganizationMapper organizationMapper; 
    
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

    @Transactional(Transactional.TxType.SUPPORTS)
    public OrganizationModelResponse getOrganization(String organizationFiscalCode){
        if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isPresent())
            return organizationMapper.convert(OrganizationEntity.find("organizationFiscalCode", organizationFiscalCode).singleResult());
        else
            throw new AppException("Not found", 404, "Organization fiscal code does not exists");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrganizationModelResponse> getOrganizations(){
        List<OrganizationEntity> orgs = OrganizationEntity.listAll();
        return orgs.stream().map(o -> organizationMapper.convert(o)).collect(Collectors.toList());
    }
}
