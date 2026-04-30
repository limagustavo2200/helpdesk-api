package com.gustavo.helpdesk_api.application.usecase.category;

import com.gustavo.helpdesk_api.core.entity.Category;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCase {

    private final CategoryRepositoryGateway repository;

    public CreateCategoryUseCase(CategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public Category execute(String name) {
        Category category = new Category(name);
        return repository.save(category);
    }


}
