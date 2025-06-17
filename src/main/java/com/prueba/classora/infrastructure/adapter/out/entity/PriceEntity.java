package com.prueba.classora.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("PRICES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEntity {

    Long brandId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer priceList;
    Long productId;
    Integer priority;
    BigDecimal price;
    String currency;
}
