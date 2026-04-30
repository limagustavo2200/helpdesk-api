package com.gustavo.helpdesk_api.infra.repository.system;

import com.gustavo.helpdesk_api.core.entity.SystemEntity;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SystemRepositoryAdapter implements SystemRepositoryGateway {

    private final SystemJpaRepository jpaRepository;

    public SystemRepositoryAdapter(SystemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public SystemEntity save(SystemEntity systemEntity) {
        return jpaRepository.save(systemEntity);
    }

    @Override
    public List<SystemEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<SystemEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<SystemEntity> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(SystemEntity system) {
        jpaRepository.delete(system);
    }

}
