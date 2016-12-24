package ru.sandarkin.roox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

import ru.sandarkin.roox.model.PartnerMapping;

@RepositoryRestResource
public interface PartnerMappingRepository extends Repository<PartnerMapping, UUID> {

  /**
   * Get partner mapping by id. For non admin users get only if it belong to current logged user.
   */
  @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.customer.id == principal")
  PartnerMapping findOne(UUID uuid);

  /**
   * Get partner mapping list. For non admin users get only if it belong to current logged user.
   */
  @Query("select pm from PartnerMapping as pm where 1 = ?#{hasRole('ROLE_ADMIN') ? 1 : 0} or pm.customer.id = ?#{principal}")
  Page<PartnerMapping> findAll(Pageable pageable);

  @PreAuthorize("hasRole('ROLE_ADMIN') or #pm.customer.id == principal")
  PartnerMapping save(@P("pm") PartnerMapping pm);

  PartnerMapping saveAndFlush(PartnerMapping pm);

  void delete(UUID uuid);
}
