package com.gustavo.helpdesk_api.infra.repository.status;

import com.gustavo.helpdesk_api.core.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusJpaRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByName(String name);
}

