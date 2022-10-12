package it.gov.pagopa.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity extends PanacheEntity{
    
    @Column(unique = true)
    private String organizationFiscalCode;

    @Column
    private LocalDateTime organizationOnboardingDate;

    public static long getOrgsByFiscalCode(String organizationFiscalCode){
        return find("organizationFiscalCode", organizationFiscalCode).count();
    }
}
