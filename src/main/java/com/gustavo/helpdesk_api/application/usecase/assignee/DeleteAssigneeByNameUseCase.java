package com.gustavo.helpdesk_api.application.usecase.assignee;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.AssigneeRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteAssigneeByNameUseCase {

    private final AssigneeRepositoryGateway repository;


    public DeleteAssigneeByNameUseCase(AssigneeRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var assignee = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));

        repository.delete(assignee);
    }
}
