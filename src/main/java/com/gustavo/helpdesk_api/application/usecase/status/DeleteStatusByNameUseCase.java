package com.gustavo.helpdesk_api.application.usecase.status;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteStatusByNameUseCase {

    private final StatusRepositoryGateway repository;


    public DeleteStatusByNameUseCase(StatusRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var status = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Status não encontrado"));

        repository.delete(status);
    }
}
