package com.gustavo.helpdesk_api.application.usecase.system;

import com.gustavo.helpdesk_api.core.entity.SystemEntity;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateSystemUseCase {

    private final SystemRepositoryGateway repository;

    public CreateSystemUseCase(SystemRepositoryGateway repository) {
        this.repository = repository;
    }

    public SystemEntity execute(String name) {
        SystemEntity systemEntity = new SystemEntity(name);
        return repository.save(systemEntity);
    }


}
