package com.gustavo.helpdesk_api.application.usecase.category;

import com.gustavo.helpdesk_api.core.entity.Category;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCategoryUseCase {

    private final CategoryRepositoryGateway repository;

    public FindAllCategoryUseCase(CategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<Category> execute() {
        var categories = repository.findAll();
        return categories;
    }
}
