package com.gustavo.helpdesk_api.infra.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gustavo.helpdesk_api.core.entity.SystemEntity;

import java.util.Optional;
import java.util.UUID;

public interface SystemJpaRepository extends JpaRepository<SystemEntity, UUID> {
    Optional<SystemEntity> findByName(String name);
}