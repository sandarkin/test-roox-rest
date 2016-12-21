package ru.sandarkin.roox.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

import ru.sandarkin.roox.model.Customer;

@RepositoryRestResource
public interface CustomerRepository extends ReadOnlyRepository<Customer, UUID> {

  @RestResource(exported = false)
  <S extends Customer> S save(S entity);

}
