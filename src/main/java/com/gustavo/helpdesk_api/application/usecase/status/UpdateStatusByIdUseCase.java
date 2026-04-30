package com.gustavo.helpdesk_api.application.usecase.status;

import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateStatusByIdUseCase {

    private final StatusRepositoryGateway repository;

    public UpdateStatusByIdUseCase(StatusRepositoryGateway repository) {
        this.repository = repository;
    }

    public Status execute(UUID id, String name) {
        var status = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Sistema não encontrado"));

        status.renameStatus(name);

        var statusUpdated = repository.save(status);

        return statusUpdated;
    }
}
