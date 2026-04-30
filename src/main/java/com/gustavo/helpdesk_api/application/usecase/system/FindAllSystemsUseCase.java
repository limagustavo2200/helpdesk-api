package com.gustavo.helpdesk_api.application.usecase.system;

import com.gustavo.helpdesk_api.core.entity.SystemEntity;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSystemsUseCase {

    private final SystemRepositoryGateway repository;

    public FindAllSystemsUseCase(SystemRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<SystemEntity> execute() {
        var systems = repository.findAll();
        return systems;
    }
}
