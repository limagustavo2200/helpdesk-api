package com.gustavo.helpdesk_api.infra.controller.status;


import com.gustavo.helpdesk_api.application.usecase.status.CreateStatusUseCase;
import com.gustavo.helpdesk_api.application.usecase.status.DeleteStatusByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.status.FindAllStatusUseCase;
import com.gustavo.helpdesk_api.application.usecase.status.UpdateStatusByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.Status;
import com.gustavo.helpdesk_api.infra.controller.status.dto.CreateStatusRequestDto;
import com.gustavo.helpdesk_api.infra.controller.status.dto.DeleteStatusRequestDto;
import com.gustavo.helpdesk_api.infra.controller.status.dto.UpdateStatusRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final CreateStatusUseCase createUseCase;
    private final DeleteStatusByNameUseCase deleteUsecase;
    private final FindAllStatusUseCase findAllStatusUseCase;
    private final UpdateStatusByIdUseCase updateUseCase;


    public StatusController(
            CreateStatusUseCase createUseCase,
            DeleteStatusByNameUseCase deleteUsecase,
            FindAllStatusUseCase findAllStatusUseCase,
            UpdateStatusByIdUseCase updateUseCase) {
        this.createUseCase = createUseCase;
        this.deleteUsecase = deleteUsecase;
        this.findAllStatusUseCase = findAllStatusUseCase;
        this.updateUseCase = updateUseCase;
    }


    @PostMapping
    public ResponseEntity<Status> create(@RequestBody CreateStatusRequestDto request) {
        var status = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @GetMapping
    public ResponseEntity<List<Status>> list() {
        var status = findAllStatusUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping
    public ResponseEntity<Status> updateById(@RequestBody UpdateStatusRequestDto request) {
        var statusUpdated = updateUseCase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(statusUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteStatusRequestDto request) {
        deleteUsecase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
