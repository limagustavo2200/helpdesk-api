package com.gustavo.helpdesk_api.application.usecase.subcategory;

import com.gustavo.helpdesk_api.core.entity.Subcategory;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateSubcategoryUseCase {

    private final SubcategoryRepositoryGateway repository;

    public CreateSubcategoryUseCase(SubcategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public Subcategory execute(String name) {
        Subcategory subcategory = new Subcategory(name);
        return repository.save(subcategory);
    }


}
