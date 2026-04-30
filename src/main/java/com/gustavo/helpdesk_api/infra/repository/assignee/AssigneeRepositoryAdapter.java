package com.gustavo.helpdesk_api.infra.repository.assignee;

import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.core.gateway.repository.AssigneeRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class AssigneeRepositoryAdapter implements AssigneeRepositoryGateway {

    private final AssigneeJpaRepository jpaRepository;

    public AssigneeRepositoryAdapter(AssigneeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Assignee save(Assignee assignee) {
        return jpaRepository.save(assignee);
    }

    @Override
    public List<Assignee> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Assignee> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Assignee> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(Assignee assignee) {
        jpaRepository.delete(assignee);
    }
}
