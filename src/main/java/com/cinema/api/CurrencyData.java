package com.cinema.api;

import java.util.List;

public record CurrencyData(String table,
                           String no,
                           String effectiveDate,
                           List<Rate>rates) {
}
