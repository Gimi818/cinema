package com.cinema.api.dto;

import java.util.List;

public record CurrencyData(String table,
                           String no,
                           String effectiveDate,
                           List<Rate>rates) {
}
