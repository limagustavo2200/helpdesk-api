package com.gustavo.helpdesk_api.infra.controller.tisector;


import com.gustavo.helpdesk_api.application.usecase.tisector.CreateTiSectorUseCase;
import com.gustavo.helpdesk_api.application.usecase.tisector.DeleteTiSectorByNameUseCase;
import com.gustavo.helpdesk_api.application.usecase.tisector.FindAllTiSectorUseCase;
import com.gustavo.helpdesk_api.application.usecase.tisector.UpdateTiSectorByIdUseCase;
import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.infra.controller.tisector.dto.CreateTiSectorRequestDto;
import com.gustavo.helpdesk_api.infra.controller.tisector.dto.DeleteTiSectorRequestDto;
import com.gustavo.helpdesk_api.infra.controller.tisector.dto.UpdateTiSectorRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ti-sector")
public class TiSectorController {

    private final CreateTiSectorUseCase createUseCase;
    private final FindAllTiSectorUseCase findAllUseCase;
    private final UpdateTiSectorByIdUseCase updateUsecase;
    private final DeleteTiSectorByNameUseCase deleteUseCase;



    public TiSectorController(
            CreateTiSectorUseCase createUseCase,
            FindAllTiSectorUseCase findAllUseCase,
            UpdateTiSectorByIdUseCase updateUsecase,
            DeleteTiSectorByNameUseCase deleteUseCase
    ) {
        this.createUseCase = createUseCase;
        this.findAllUseCase = findAllUseCase;
        this.updateUsecase = updateUsecase;
        this.deleteUseCase = deleteUseCase;

    }

    @PostMapping
    public ResponseEntity<TiSector> create(@RequestBody CreateTiSectorRequestDto request) {
        var sector = createUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(sector);
    }

    @GetMapping
    public ResponseEntity<List<TiSector>> list() {
        var sectors = findAllUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(sectors);
    }

    @PutMapping
    public ResponseEntity<TiSector> updateById(@RequestBody UpdateTiSectorRequestDto request) {
        var sectorUpdated = updateUsecase.execute(request.id(), request.name());
        return ResponseEntity.status(HttpStatus.OK).body(sectorUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestBody DeleteTiSectorRequestDto request) {
        deleteUseCase.execute(request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
