package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.SystemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepositoryGateway {

    SystemEntity save(SystemEntity systemEntity);
    List<SystemEntity> findAll();
    Optional<SystemEntity> findById(UUID id);
    Optional<SystemEntity> findByName(String name);
    void delete(SystemEntity system);
}
