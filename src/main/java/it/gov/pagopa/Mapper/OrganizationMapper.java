package it.gov.pagopa.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Model.OrganizationModelResponse;

@Mapper(componentModel = "cdi")
public interface OrganizationMapper {
    @Mapping(target = "organizationFiscalCode", source = "rowKey")
    @Mapping(target = "organizationOnboardingDate", source = "organizationOnboardingDate")
    OrganizationModelResponse convert(OrganizationEntity organizationEntity);
}
