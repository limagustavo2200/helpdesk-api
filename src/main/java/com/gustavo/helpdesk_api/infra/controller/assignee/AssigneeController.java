package com.gustavo.helpdesk_api.infra.controller.assignee;


import com.gustavo.helpdesk_api.application.usecase.assignee.CreateAssigneeUseCase;
import com.gustavo.helpdesk_api.application.usecase.assignee.DeleteAssigneeByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.assignee.FindAllAssigneeUseCase;
import com.gustavo.helpdesk_api.application.usecase.assignee.UpdateAssigneeByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.infra.controller.assignee.dto.CreateAssigneeRequestDto;
import com.gustavo.helpdesk_api.infra.controller.assignee.dto.DeleteAssigneeRequestDto;
import com.gustavo.helpdesk_api.infra.controller.assignee.dto.UpdateAssigneeRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignee")
public class AssigneeController {

    private final CreateAssigneeUseCase createUseCase;
    private final UpdateAssigneeByIdUseCase updateUseCase;
    private final FindAllAssigneeUseCase findAllUseCase;
    private final DeleteAssigneeByNameUseCase deleteUseCase;

    public AssigneeController(
            CreateAssigneeUseCase createUseCase,
            UpdateAssigneeByIdUseCase updateUseCase,
            FindAllAssigneeUseCase findAllUseCase,
            DeleteAssigneeByNameUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.findAllUseCase = findAllUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Assignee> create(@RequestBody CreateAssigneeRequestDto request) {
        var assignee = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignee);
    }

    @GetMapping
    public ResponseEntity<List<Assignee>> list() {
        var assignee = findAllUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(assignee);
    }

    @PutMapping
    public ResponseEntity<Assignee> updateById(@RequestBody UpdateAssigneeRequestDto request) {
        var assigneeUpdated = updateUseCase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(assigneeUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteAssigneeRequestDto request) {
        deleteUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
