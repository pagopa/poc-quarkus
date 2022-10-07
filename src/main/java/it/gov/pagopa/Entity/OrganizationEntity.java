package it.gov.pagopa.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class OrganizationEntity extends PanacheEntityBase{
    
    @Id
    @Column(name = "organizationFiscalCode")
    private String organizationFiscalCode;

    @Column(name = "organizationOnboardingDate")
    private String organizationOnboardingDate;
}
