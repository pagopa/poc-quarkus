package it.gov.pagopa.Entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.*;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Table(name = "organizations")
public class OrganizationEntity extends PanacheMongoEntity {

    @BsonProperty("organizationFiscalCode")
    @Column(unique = true)
    private String organizationFiscalCode;

    @BsonProperty("organizationOnboardingDate")
    @Column
    private LocalDateTime organizationOnboardingDate;

    public static Optional<OrganizationEntity> getOrgsByFiscalCode(String organizationFiscalCode){
        return find("organizationFiscalCode", organizationFiscalCode).firstResultOptional();
    }
}
