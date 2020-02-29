package cc.vivp.bankrupt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cc.vivp.bankrupt.model.db.CustomerEntity;

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomersRepository extends PagingAndSortingRepository<CustomerEntity, Long> {}
