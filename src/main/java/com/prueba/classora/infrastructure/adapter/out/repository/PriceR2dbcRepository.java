package com.prueba.classora.infrastructure.adapter.out.repository;

import com.prueba.classora.infrastructure.adapter.out.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
       SELECT id,
              brand_id,
              start_date,
              end_date,
              price_list,
              product_id,
              priority,
              price,
              currency
       FROM prices
       WHERE brand_id   = :brandId
         AND product_id = :productId
         AND :applicationDate BETWEEN start_date AND end_date
       ORDER BY priority DESC, price_list DESC
       LIMIT 1
       """)
    Mono<PriceEntity> findApplicable(@Param("applicationDate") LocalDateTime applicationDate,
                                     @Param("productId")      Long          productId,
                                     @Param("brandId")        Integer       brandId);

}
