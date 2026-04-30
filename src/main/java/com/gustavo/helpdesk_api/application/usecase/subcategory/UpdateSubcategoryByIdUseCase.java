package com.gustavo.helpdesk_api.application.usecase.subcategory;


import com.gustavo.helpdesk_api.core.entity.Subcategory;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateSubcategoryByIdUseCase {

    private final SubcategoryRepositoryGateway repository;

    public UpdateSubcategoryByIdUseCase(SubcategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public Subcategory execute(UUID id, String name) {
        var subcategory = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));

        subcategory.renameSubcategory(name);

        var subcategoryUpdated = repository.save(subcategory);

        return subcategoryUpdated;
    }
}
