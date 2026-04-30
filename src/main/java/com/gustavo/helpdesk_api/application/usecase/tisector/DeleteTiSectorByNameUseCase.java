package com.gustavo.helpdesk_api.application.usecase.tisector;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.TiSectorRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteTiSectorByNameUseCase {

    private final TiSectorRepositoryGateway repository;


    public DeleteTiSectorByNameUseCase(TiSectorRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var tiSector = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Setor T.I não encontrado"));

        repository.delete(tiSector);
    }
}
