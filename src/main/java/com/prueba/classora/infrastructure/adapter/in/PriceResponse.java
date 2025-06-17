package com.prueba.classora.infrastructure.adapter.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(Integer brandId,
                            Long productId,
                            Integer priceList,
                            LocalDateTime startDate,
                            LocalDateTime endDate,
                            BigDecimal price,
                            String currency) {
}
