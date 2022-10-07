package it.gov.pagopa.Service;


import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.logging.Log;
import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppException;

@ApplicationScoped
public class EnrollmentsService {
    
    @Transactional
    public void createOrganization(String organizationFiscalCode){
        OrganizationEntity orgEnt = new OrganizationEntity();
        orgEnt.setOrganizationFiscalCode(organizationFiscalCode);
        orgEnt.setOrganizationOnboardingDate(LocalDateTime.now().toString());
        orgEnt.persist();
    }

    @Transactional
    public boolean getOrganizations(String organizationFiscalCode){
        return OrganizationEntity.findById(organizationFiscalCode) != null;
    }
}
