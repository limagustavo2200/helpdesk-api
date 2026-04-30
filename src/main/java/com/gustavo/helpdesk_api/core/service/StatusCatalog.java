package com.gustavo.helpdesk_api.core.service;

import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.StatusRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class StatusCatalog {

    private final StatusRepositoryGateway repository;

    public StatusCatalog(StatusRepositoryGateway repository) {
        this.repository = repository;
    }

    public Status open() {
        return repository.findByName("Aberto")
                .orElseThrow(() -> new ResourceNotFoundException("Status aberto não configurado."));
    }
}
