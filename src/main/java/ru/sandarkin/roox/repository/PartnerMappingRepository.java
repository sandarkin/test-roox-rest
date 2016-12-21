package ru.sandarkin.roox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

import ru.sandarkin.roox.model.PartnerMapping;

@RepositoryRestResource
public interface PartnerMappingRepository extends JpaRepository<PartnerMapping, UUID> {

}
