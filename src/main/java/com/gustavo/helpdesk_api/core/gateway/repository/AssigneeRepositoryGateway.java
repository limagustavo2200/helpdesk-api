package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.Assignee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssigneeRepositoryGateway {

    Assignee save(Assignee assignee);
    List<Assignee> findAll();
    Optional<Assignee> findById(UUID id);
    Optional<Assignee> findByName(String name);
    void delete(Assignee assignee);
}
