package com.gustavo.helpdesk_api.application.usecase.subcategory;


import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteSubcategoryByNameUseCase {

    private final SubcategoryRepositoryGateway repository;


    public DeleteSubcategoryByNameUseCase(SubcategoryRepositoryGateway repository) {
        this.repository = repository;
    }

    public void execute(String name) {
        var subcategory = repository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));

        repository.delete(subcategory);
    }
}
