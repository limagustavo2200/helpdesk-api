package com.gustavo.helpdesk_api.application.usecase.tisector;


import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.TiSectorRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTiSectorByIdUseCase {

    private final TiSectorRepositoryGateway repository;

    public UpdateTiSectorByIdUseCase(TiSectorRepositoryGateway repository) {
        this.repository = repository;
    }

    public TiSector execute(UUID id, String name) {
        var tiSector = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Setor T.I não encontrado"));

        tiSector.renameTiSector(name);

        var sectorUpdated = repository.save(tiSector);

        return sectorUpdated;
    }
}
