package com.gustavo.helpdesk_api.core.gateway.repository;


import com.gustavo.helpdesk_api.core.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryGateway {

    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(UUID id);
    Optional<Category> findByName(String name);
    void delete(Category category);
}
