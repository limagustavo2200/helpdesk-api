package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.TiSector;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TiSectorRepositoryGateway {


    TiSector save(TiSector tiSector);
    List<TiSector> findAll();
    Optional<TiSector> findById(UUID id);
    Optional<TiSector> findByName(String name);
    void delete(TiSector tiSector);
}
