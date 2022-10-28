package it.gov.pagopa.Entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
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

    public static Optional<OrganizationEntity> getOrgsByFiscalCode(String organizationFiscalCode){
        return find("organizationFiscalCode", organizationFiscalCode).singleResultOptional();
    }
}
