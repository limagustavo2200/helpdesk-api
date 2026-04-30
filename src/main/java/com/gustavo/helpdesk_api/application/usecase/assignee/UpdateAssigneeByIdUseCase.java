package com.gustavo.helpdesk_api.application.usecase.assignee;


import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.AssigneeRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateAssigneeByIdUseCase {

    private final AssigneeRepositoryGateway repository;

    public UpdateAssigneeByIdUseCase(AssigneeRepositoryGateway repository) {
        this.repository = repository;
    }

    public Assignee execute(UUID id, String name) {
        var assignee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reponsável não encontrada"));

        assignee.renameAssignee(name);

        var assigneeUpdated = repository.save(assignee);

        return assigneeUpdated;
    }
}
