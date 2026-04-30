package com.gustavo.helpdesk_api.application.usecase.system;

import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import org.springframework.stereotype.Service;
import com.gustavo.helpdesk_api.core.entity.SystemEntity;

import java.util.UUID;

@Service
public class UpdateSystemByIdUseCase {

    private final SystemRepositoryGateway repository;

    public UpdateSystemByIdUseCase(SystemRepositoryGateway repository) {
        this.repository = repository;
    }

    public SystemEntity execute(UUID id, String name) {
        var system = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Sistema não encontrado"));

        system.renameSystem(name);

        var systemUpdated = repository.save(system);

        return systemUpdated;
    }
}
