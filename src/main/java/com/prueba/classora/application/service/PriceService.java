package com.prueba.classora.application.service;

import com.prueba.classora.application.dto.PriceDto;
import com.prueba.classora.domain.port.in.GetPriceQuery;
import reactor.core.publisher.Mono;

public interface PriceService {
    Mono<PriceDto> getPrice(GetPriceQuery query);
}
