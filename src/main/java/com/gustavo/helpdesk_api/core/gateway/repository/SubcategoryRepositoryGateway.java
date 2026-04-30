package com.gustavo.helpdesk_api.core.gateway.repository;


import com.gustavo.helpdesk_api.core.entity.Subcategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubcategoryRepositoryGateway {

    Subcategory save(Subcategory subcategory);
    List<Subcategory> findAll();
    Optional<Subcategory> findById(UUID id);
    Optional<Subcategory> findByName(String name);
    void delete(Subcategory subcategory);
}
