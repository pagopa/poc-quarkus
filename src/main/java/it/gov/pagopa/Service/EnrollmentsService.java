package it.gov.pagopa.Service;


import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import it.gov.pagopa.Entity.OrganizationEntity;
import it.gov.pagopa.Exception.AppError;
import it.gov.pagopa.Exception.AppException;
import it.gov.pagopa.Mapper.OrganizationMapper;
import it.gov.pagopa.Model.OrganizationModelResponse;
import it.gov.pagopa.Util.AzuriteStorageUtil;
import it.gov.pagopa.Config.Configs;

@ApplicationScoped
public class EnrollmentsService {
    
    private String storageConnectionString;

	private String organizationsTable;

    @Inject
    OrganizationMapper organizationMapper;

	@Inject
	public EnrollmentsService(Configs configs){
		super();
        this.storageConnectionString = configs.connection();
        this.organizationsTable = configs.table();
        try {
        	AzuriteStorageUtil azuriteStorageUtil = new AzuriteStorageUtil(configs.connection());
			azuriteStorageUtil.createTable(configs.table());
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			Log.error("Error in environment initializing", e);
		}
	}

    @Transactional
    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
    	OrganizationEntity resultOrganizationEntity = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntity = (OrganizationEntity) table.execute(TableOperation.insert(new OrganizationEntity(organizationFiscalCode, LocalDateTime.now().toString()))).getResult();
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			if (e instanceof StorageException && ((StorageException) e).getHttpStatusCode() == 409) {
				throw new AppException(AppError.ORGANIZATION_DUPLICATED, organizationFiscalCode);
			}
			// unexpected error
			Log.error("Error in processing create organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
		return organizationMapper.convert(resultOrganizationEntity);
        
    }

    @Transactional
    public void removeOrganization(String organizationFiscalCode) {
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			table.execute(TableOperation.delete(new OrganizationEntity(organizationFiscalCode)));
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			if (e instanceof StorageException && ((StorageException) e).getHttpStatusCode() == 404) {
				throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
			}
			// unexpected error
			Log.error("Error in processing delete organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
        
    }
    
    @Transactional(Transactional.TxType.SUPPORTS)
    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
    	OrganizationEntity resultOrganizationEntity = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntity = Optional.ofNullable(
					(OrganizationEntity) table.execute(TableOperation.retrieve(OrganizationEntity.ORGANIZATION_KEY, organizationFiscalCode, OrganizationEntity.class)).getResult())
			.orElseThrow(() -> new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode));
			
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			// unexpected error
			Log.error("Error in processing get organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
		return organizationMapper.convert(resultOrganizationEntity);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrganizationModelResponse> getOrganizations() {
    	Spliterator<OrganizationEntity> resultOrganizationEntityList = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntityList = 
					table.execute(TableQuery.from(OrganizationEntity.class).where((TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, OrganizationEntity.ORGANIZATION_KEY)))).spliterator();
			
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			// unexpected error
			Log.error("Error in processing get organizations list", e);
			throw new AppException(AppError.INTERNAL_ERROR, "ALL");
		} 
        List<OrganizationEntity> orgs = StreamSupport.stream(resultOrganizationEntityList, false).collect(Collectors.toList());
		return orgs.stream().map(o -> organizationMapper.convert(o)).collect(Collectors.toList());
        
    }
}
