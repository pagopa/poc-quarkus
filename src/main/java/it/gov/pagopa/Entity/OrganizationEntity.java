package it.gov.pagopa.Entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.microsoft.azure.storage.table.TableServiceEntity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity extends TableServiceEntity {

    private String organizationOnboardingDate;
    public static final String ORGANIZATION_KEY = "organization";

    public OrganizationEntity(String organizationId, String organizationOnboardingDate) {
        this.partitionKey = ORGANIZATION_KEY;
        this.rowKey = organizationId;
        this.organizationOnboardingDate = organizationOnboardingDate;
    }

    public OrganizationEntity(String organizationId) {
        this.partitionKey = ORGANIZATION_KEY;
        this.rowKey = organizationId;
        // https://docs.microsoft.com/en-us/dotnet/api/microsoft.azure.cosmos.table.tableentity.etag?view=azure-dotnet#microsoft-azure-cosmos-table-tableentity-etag
        this.etag = "*";
    }
}
