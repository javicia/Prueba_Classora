package com.prueba.classora.infrastructure.persistence.repository;

import com.prueba.classora.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
        SELECT * FROM PRICES
         WHERE BRAND_ID = :brandId
           AND PRODUCT_ID = :productId
           AND :applicationDate BETWEEN START_DATE AND END_DATE
         ORDER BY PRIORITY DESC, PRICE_LIST DESC
         LIMIT 1
    """)
    Mono<PriceEntity> findApplicablePrice(Long brandId, Long productId, LocalDate applicationDate);

}
