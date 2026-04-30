package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatusRepositoryGateway {

    Status save(Status status);
    List<Status> findAll();
    Optional<Status> findById(UUID id);
    Optional<Status> findByName(String name);
    void delete(Status status);
}
