package com.gustavo.helpdesk_api.application.usecase.tisector;

import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.core.gateway.repository.TiSectorRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateTiSectorUseCase {

    private final TiSectorRepositoryGateway repository;

    public CreateTiSectorUseCase(TiSectorRepositoryGateway repository) {
        this.repository = repository;
    }

    public TiSector execute(String name) {
        TiSector tiSector = new TiSector(name);
        return repository.save(tiSector);
    }


}
