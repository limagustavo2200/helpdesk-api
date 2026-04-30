package com.gustavo.helpdesk_api.core.service;

import com.gustavo.helpdesk_api.core.util.TextUtil;
import com.gustavo.helpdesk_api.core.valueobject.Priority;

import java.util.List;


public class PriorityCatalog {

    public static List<Priority> all() {
        return List.of(
                Priority.low(),
                Priority.medium(),
                Priority.high(),
                Priority.urgent()
        );
    }

    public static Priority fromLabel(String label) {

        String normalizedInput = TextUtil.normalize(label);

        return all().stream()
                .filter(priority ->
                        TextUtil.normalize(priority.getLabel())
                                .equals(normalizedInput)
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Prioridade inválida"));
    }
}