package com.gustavo.helpdesk_api.infra.controller.system;

import com.gustavo.helpdesk_api.application.usecase.system.CreateSystemUseCase;
import com.gustavo.helpdesk_api.application.usecase.system.DeleteSystemByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.system.FindAllSystemsUseCase;
import com.gustavo.helpdesk_api.application.usecase.system.UpdateSystemByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.SystemEntity;
import com.gustavo.helpdesk_api.infra.controller.system.dto.CreateSystemRequestDto;
import com.gustavo.helpdesk_api.infra.controller.system.dto.DeleteSystemRequestDto;
import com.gustavo.helpdesk_api.infra.controller.system.dto.UpdateSystemRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    private final CreateSystemUseCase createUseCase;
    private final FindAllSystemsUseCase findAllUseCase;
    private final UpdateSystemByIdUseCase updateSystemUsecase;
    private final DeleteSystemByNameUseCase deleteSystemByNameUseCase;



    public SystemController(
            CreateSystemUseCase createUseCase,
            FindAllSystemsUseCase findAllUseCase,
            UpdateSystemByIdUseCase updateSystemUsecase,
            DeleteSystemByNameUseCase deleteSystemByNameUseCase
    ) {
        this.createUseCase = createUseCase;
        this.findAllUseCase = findAllUseCase;
        this.updateSystemUsecase = updateSystemUsecase;
        this.deleteSystemByNameUseCase = deleteSystemByNameUseCase;

    }

    @PostMapping
    public ResponseEntity<SystemEntity> create(@RequestBody CreateSystemRequestDto request) {
        var system = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(system);
    }

    @GetMapping
    public ResponseEntity<List<SystemEntity>> list() {
        var systems = findAllUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(systems);
    }

    @PutMapping
    public ResponseEntity<SystemEntity> updateById(@RequestBody UpdateSystemRequestDto request) {
        var systemUpdated = updateSystemUsecase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(systemUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteSystemRequestDto request) {
        deleteSystemByNameUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
