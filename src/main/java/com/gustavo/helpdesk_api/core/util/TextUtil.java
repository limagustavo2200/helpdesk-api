package com.gustavo.helpdesk_api.core.util;

import java.text.Normalizer;

public class TextUtil {

    public static String normalize(String value) {
        if (value == null) return null;

        String normalized = Normalizer.normalize(value.trim(), Normalizer.Form.NFD);

        return normalized
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }
}