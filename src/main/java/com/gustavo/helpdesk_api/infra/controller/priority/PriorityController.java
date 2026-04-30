package com.gustavo.helpdesk_api.infra.controller.priority;


import com.gustavo.helpdesk_api.core.service.PriorityCatalog;
import com.gustavo.helpdesk_api.core.valueobject.Priority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    @GetMapping
    public ResponseEntity<List<Priority>> list() {
        var priorities = PriorityCatalog.all();
        return ResponseEntity.status(HttpStatus.OK).body(priorities);
    }
}
