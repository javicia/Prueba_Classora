package com.prueba.classora.domain.port.out;

import com.prueba.classora.domain.model.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceRepositoryPort {
    Mono<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId);
}
