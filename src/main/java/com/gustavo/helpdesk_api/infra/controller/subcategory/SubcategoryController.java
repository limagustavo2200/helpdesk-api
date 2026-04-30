package com.gustavo.helpdesk_api.infra.controller.subcategory;


import com.gustavo.helpdesk_api.application.usecase.subcategory.CreateSubcategoryUseCase;
import com.gustavo.helpdesk_api.application.usecase.subcategory.DeleteSubcategoryByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.subcategory.FindAllSubcategoryUseCase;
import com.gustavo.helpdesk_api.application.usecase.subcategory.UpdateSubcategoryByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.Subcategory;
import com.gustavo.helpdesk_api.infra.controller.subcategory.dto.CreateSubcategoryRequestDto;
import com.gustavo.helpdesk_api.infra.controller.subcategory.dto.DeleteSubcategoryRequestDto;
import com.gustavo.helpdesk_api.infra.controller.subcategory.dto.UpdateSubcategoryRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {

    private final CreateSubcategoryUseCase createUseCase;
    private final UpdateSubcategoryByIdUseCase updateUseCase;
    private final FindAllSubcategoryUseCase findAllUseCase;
    private final DeleteSubcategoryByNameUseCase deleteUseCase;

    public SubcategoryController(
            CreateSubcategoryUseCase createUseCase,
            UpdateSubcategoryByIdUseCase updateUseCase,
            FindAllSubcategoryUseCase findAllUseCase,
            DeleteSubcategoryByNameUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.findAllUseCase = findAllUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Subcategory> create(@RequestBody CreateSubcategoryRequestDto request) {
        var subcategory = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(subcategory);
    }

    @GetMapping
    public ResponseEntity<List<Subcategory>> list() {
        var subcategory = findAllUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(subcategory);
    }

    @PutMapping
    public ResponseEntity<Subcategory> updateById(@RequestBody UpdateSubcategoryRequestDto request) {
        var subcategoryUpdated = updateUseCase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(subcategoryUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteSubcategoryRequestDto request) {
        deleteUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
