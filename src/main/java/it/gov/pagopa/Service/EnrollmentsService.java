package it.gov.pagopa.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import io.quarkus.logging.Log;
import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppException;

@ApplicationScoped
public class EnrollmentsService {
    
    @Transactional
    public void createOrganization(String organizationFiscalCode){
        OrganizationEntity orgEnt = new OrganizationEntity();
        try{
            orgEnt.setOrganizationFiscalCode(organizationFiscalCode);
            orgEnt.setOrganizationOnboardingDate(LocalDateTime.now().toString());
            orgEnt.persistAndFlush();
        }catch(Exception e){
            if(e instanceof PersistenceException)
                throw new AppException("Conflict", 409, "Fiscal code already present");
            else
                throw new AppException("Generic error", 500, "Internal server error");
        }
    }
}
