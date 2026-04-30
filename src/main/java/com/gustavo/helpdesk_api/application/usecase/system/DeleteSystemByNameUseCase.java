package com.gustavo.helpdesk_api.application.usecase.system;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteSystemByNameUseCase {

    private final SystemRepositoryGateway repository;


    public DeleteSystemByNameUseCase(SystemRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var system = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Sistem não encontrado"));

        repository.delete(system);
    }
}
