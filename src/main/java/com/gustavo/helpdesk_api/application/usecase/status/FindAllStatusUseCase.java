package com.gustavo.helpdesk_api.application.usecase.status;

import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllStatusUseCase {

    private final StatusRepositoryGateway repository;

    public FindAllStatusUseCase(StatusRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<Status> execute() {
        var status = repository.findAll();
        return status;
    }
}
