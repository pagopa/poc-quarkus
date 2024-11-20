package it.gov.pagopa.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppError;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Mapper.OrganizationMapper;
import it.gov.pagopa.Model.OrganizationModelResponse;

@ApplicationScoped
public class EnrollmentsService {

    @Inject
    OrganizationMapper organizationMapper;

    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
    	OrganizationEntity resultOrganizationEntity = new OrganizationEntity(organizationFiscalCode, LocalDateTime.now());
		if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isEmpty()){
			resultOrganizationEntity.persist();
			return organizationMapper.convert(resultOrganizationEntity);
		}
        else{
			throw new AppException(AppError.ORGANIZATION_DUPLICATED, organizationFiscalCode);
		}
    }

    public void removeOrganization(String organizationFiscalCode) {
		if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isPresent()){
			OrganizationEntity.delete("organizationFiscalCode", organizationFiscalCode);
		}
		else{
			throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
		}
    }

	public OrganizationModelResponse getOrganization(String organizationFiscalCode){
		if(OrganizationEntity.getOrgsByFiscalCode(organizationFiscalCode).isPresent())
			return organizationMapper.convert(OrganizationEntity.find("organizationFiscalCode", organizationFiscalCode).singleResult());
		else
			throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
	}

	public List<OrganizationModelResponse> getOrganizations(){
		List<OrganizationEntity> orgs = OrganizationEntity.listAll();
		return orgs.stream().map(o -> organizationMapper.convert(o)).collect(Collectors.toList());
	}
}
