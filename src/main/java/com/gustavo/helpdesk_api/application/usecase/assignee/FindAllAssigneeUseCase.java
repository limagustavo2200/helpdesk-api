package com.gustavo.helpdesk_api.application.usecase.assignee;

import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.core.gateway.repository.AssigneeRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllAssigneeUseCase {

    private final AssigneeRepositoryGateway repository;

    public FindAllAssigneeUseCase(AssigneeRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<Assignee> execute() {
        var assignees = repository.findAll();
        return assignees;
    }
}
