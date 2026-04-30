package com.gustavo.helpdesk_api.application.usecase.status;

import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateStatusUseCase {

    private final StatusRepositoryGateway repository;

    public CreateStatusUseCase(StatusRepositoryGateway repository) {
        this.repository = repository;
    }

    public Status execute(String name) {
        Status status = new Status(name);
        return repository.save(status);
    }


}
