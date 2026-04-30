package com.gustavo.helpdesk_api.application.usecase.category;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryByNameUseCase {

    private final CategoryRepositoryGateway repository;


    public DeleteCategoryByNameUseCase(CategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var category = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        repository.delete(category);
    }
}
