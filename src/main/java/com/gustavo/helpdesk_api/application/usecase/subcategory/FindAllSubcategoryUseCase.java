package com.gustavo.helpdesk_api.application.usecase.subcategory;

import com.gustavo.helpdesk_api.core.entity.Subcategory;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSubcategoryUseCase {

    private final SubcategoryRepositoryGateway repository;

    public FindAllSubcategoryUseCase(SubcategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<Subcategory> execute() {
        var subcategories = repository.findAll();
        return subcategories;
    }
}
