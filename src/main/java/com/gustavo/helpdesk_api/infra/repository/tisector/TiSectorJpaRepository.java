package com.gustavo.helpdesk_api.infra.repository.tisector;

import com.gustavo.helpdesk_api.core.entity.TiSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TiSectorJpaRepository extends JpaRepository<TiSector, UUID> {
    Optional<TiSector> findByName(String name);
}