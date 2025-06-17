package com.prueba.classora.infrastructure.adapter.out;


import com.prueba.classora.domain.model.Price;
import com.prueba.classora.domain.port.out.PriceRepositoryPort;
import com.prueba.classora.infrastructure.adapter.out.repository.PriceR2dbcRepository;
import com.prueba.classora.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceR2dbcRepository repository;
    private final PriceMapper mapper;

    @Override
    public Mono<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        return repository.findApplicable(applicationDate, productId, brandId)
                .map(mapper::toDomain);
    }
}
