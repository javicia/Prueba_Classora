package com.prueba.classora.application.service.impl;

import com.prueba.classora.application.dto.PriceDto;
import com.prueba.classora.application.exception.PriceNotFoundException;
import com.prueba.classora.application.service.PriceService;
import com.prueba.classora.domain.port.in.GetPriceQuery;
import com.prueba.classora.domain.port.out.PriceRepositoryPort;
import com.prueba.classora.infrastructure.mapper.PriceMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepositoryPort repositoryPort;
    private final PriceMapper mapper;

    @Override
    @CircuitBreaker(name = "priceService", fallbackMethod = "fallbackGetPrice")
    public Mono<PriceDto> getPrice(GetPriceQuery query) {
        return repositoryPort.findApplicablePrice(query.applicationDate(), query.productId(), query.brandId())
                .switchIfEmpty(Mono.error(
                        new PriceNotFoundException("No existe tarifa para los criterios especificados")))
                .map(mapper::toDto);
    }
    /* fallback para el Circuit Breaker */
    private Mono<PriceDto> fallbackGetPrice(@SuppressWarnings("unused") GetPriceQuery query, Throwable ex) {
        return Mono.error(new RuntimeException("Servicio no disponible. Inténtelo más tarde.", ex));
    }
}
