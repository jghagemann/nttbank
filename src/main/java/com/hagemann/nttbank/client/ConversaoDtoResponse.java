package com.hagemann.nttbank.client;

import java.math.BigDecimal;
import java.util.Map;

public record ConversaoDtoResponse(

        Boolean success,

        Integer timestamp,

        String base,

        String date,

        Map<String, BigDecimal> rates) {
}
