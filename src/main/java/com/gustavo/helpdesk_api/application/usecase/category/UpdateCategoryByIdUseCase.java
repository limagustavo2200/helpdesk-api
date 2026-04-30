package com.gustavo.helpdesk_api.application.usecase.category;


import com.gustavo.helpdesk_api.core.entity.Category;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCategoryByIdUseCase {

    private final CategoryRepositoryGateway repository;

    public UpdateCategoryByIdUseCase(CategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public Category execute(UUID id, String name) {
        var category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        category.renameCategory(name);

        var categoryUpdated = repository.save(category);

        return categoryUpdated;
    }
}
