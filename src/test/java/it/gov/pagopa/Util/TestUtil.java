package it.gov.pagopa.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.gov.pagopa.Model.OrganizationModelResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

    private String mockOrganizationFiscalCode;
    
    public static OrganizationModelResponse getMockOrganizationEntity() {
		return OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode).
				organizationOnboardingDate(LocalDateTime.now()).
				build();
	}
	
	public static List<OrganizationModelResponse> getMockOrganizationEntityList() {
		ArrayList<OrganizationModelResponse> list = new ArrayList<>();
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_1").
				organizationOnboardingDate(LocalDateTime.now()).
				build());
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_2").
				organizationOnboardingDate(LocalDateTime.now()).
				build());
		list.add(OrganizationModelResponse.builder().
				organizationFiscalCode(mockOrganizationFiscalCode+"_3").
				organizationOnboardingDate(LocalDateTime.now()).
				build());
		return list;
	}
}
