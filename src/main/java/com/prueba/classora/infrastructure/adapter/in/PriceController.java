package com.prueba.classora.infrastructure.adapter.in.rest;

import com.prueba.classora.application.service.PriceService;
import com.prueba.classora.domain.port.in.GetPriceQuery;
import com.prueba.classora.infrastructure.adapter.in.PriceResponse;
import com.prueba.classora.infrastructure.persistence.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService service;
    private final PriceMapper mapper;

    @GetMapping
    public Mono<PriceResponse> getPrice(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                        @RequestParam Long productId,
                                        @RequestParam Long brandId) {
        GetPriceQuery query = new GetPriceQuery(applicationDate, productId, brandId);
        return service.getPrice(query).map(mapper::toResponse);
    }
}
