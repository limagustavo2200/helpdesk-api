package com.gustavo.helpdesk_api.infra.repository.tisector;

import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.core.gateway.repository.TiSectorRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class TiSectorRepositoryAdapter implements TiSectorRepositoryGateway {

    private final TiSectorJpaRepository jpaRepository;

    public TiSectorRepositoryAdapter(TiSectorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TiSector save(TiSector tiSector) {
        return jpaRepository.save(tiSector);
    }

    @Override
    public List<TiSector> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<TiSector> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<TiSector> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(TiSector tiSector) {
        jpaRepository.delete(tiSector);
    }
}
