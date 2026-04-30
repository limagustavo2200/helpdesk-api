package com.gustavo.helpdesk_api.infra.repository.subcategory;

import com.gustavo.helpdesk_api.core.entity.Subcategory;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class SubcategoryRepositoryAdapter implements SubcategoryRepositoryGateway {

    private final SubcategoryJpaRepository jpaRepository;

    public SubcategoryRepositoryAdapter(SubcategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Subcategory save(Subcategory subcategory) {
        return jpaRepository.save(subcategory);
    }

    @Override
    public List<Subcategory> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Subcategory> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Subcategory> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(Subcategory subcategory) {
        jpaRepository.delete(subcategory);
    }
}
