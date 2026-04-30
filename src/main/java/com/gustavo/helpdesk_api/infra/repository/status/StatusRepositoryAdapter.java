package com.gustavo.helpdesk_api.infra.repository.status;

import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class StatusRepositoryAdapter implements StatusRepositoryGateway {

    private final StatusJpaRepository jpaRepository;

    public StatusRepositoryAdapter(StatusJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Status save(Status status) {
        return jpaRepository.save(status);
    }

    @Override
    public List<Status> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Status> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Status> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(Status status) {
        jpaRepository.delete(status);
    }
}
