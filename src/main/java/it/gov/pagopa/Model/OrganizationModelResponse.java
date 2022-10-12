package it.gov.pagopa.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModelResponse {
    
    private String organizationFiscalCode;

    private LocalDateTime organizationOnboardingDate;
}
