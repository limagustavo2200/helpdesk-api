package com.gustavo.helpdesk_api.application.usecase.tisector;


import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.core.gateway.repository.TiSectorRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllTiSectorUseCase {

    private final TiSectorRepositoryGateway repository;

    public FindAllTiSectorUseCase(TiSectorRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<TiSector> execute() {
        var tiSectors = repository.findAll();
        return tiSectors;
    }
}
