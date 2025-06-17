package com.prueba.classora.domain.port.in;

import java.time.LocalDateTime;

public record GetPriceQuery(LocalDateTime applicationDate, Long productId, Integer
                            brandId) { }
