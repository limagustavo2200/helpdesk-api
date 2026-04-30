package com.gustavo.helpdesk_api.infra.controller.category;


import com.gustavo.helpdesk_api.application.usecase.category.CreateCategoryUseCase;
import com.gustavo.helpdesk_api.application.usecase.category.DeleteCategoryByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.category.FindAllCategoryUseCase;
import com.gustavo.helpdesk_api.application.usecase.category.UpdateCategoryByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.Category;
import com.gustavo.helpdesk_api.infra.controller.category.dto.CreateCategoryRequestDto;
import com.gustavo.helpdesk_api.infra.controller.category.dto.DeleteCategoryRequestDto;
import com.gustavo.helpdesk_api.infra.controller.category.dto.UpdateCategoryRequestDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CreateCategoryUseCase createUseCase;
    private final UpdateCategoryByIdUseCase updateUseCase;
    private final FindAllCategoryUseCase findAllUseCase;
    private final DeleteCategoryByNameUseCase deleteUseCase;

    public CategoryController(
            CreateCategoryUseCase createUseCase,
            UpdateCategoryByIdUseCase updateUseCase,
            FindAllCategoryUseCase findAllUseCase,
            DeleteCategoryByNameUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.findAllUseCase = findAllUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CreateCategoryRequestDto request) {
        var category = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> list() {
        var category = findAllUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PutMapping
    public ResponseEntity<Category> updateById(@RequestBody UpdateCategoryRequestDto request) {
        var categoryUpdated = updateUseCase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(categoryUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteCategoryRequestDto request) {
        deleteUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
