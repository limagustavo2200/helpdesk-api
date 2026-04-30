package com.gustavo.helpdesk_api.infra.repository.assignee;

import com.gustavo.helpdesk_api.core.entity.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssigneeJpaRepository extends JpaRepository<Assignee, UUID> {
    Optional<Assignee> findByName(String name);
}