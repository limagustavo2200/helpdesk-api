package com.gustavo.helpdesk_api.application.usecase.assignee;

import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.core.gateway.repository.AssigneeRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateAssigneeUseCase {

    private final AssigneeRepositoryGateway repository;

    public CreateAssigneeUseCase(AssigneeRepositoryGateway repository) {
        this.repository = repository;
    }

    public Assignee execute(String name) {
        Assignee assignee = new Assignee(name);
        return repository.save(assignee);
    }


}
