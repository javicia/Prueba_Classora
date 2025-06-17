package com.prueba.classora.infrastructure.mapper;

import com.prueba.classora.application.dto.PriceDto;
import com.prueba.classora.domain.model.Price;
import com.prueba.classora.infrastructure.adapter.in.PriceResponse;
import com.prueba.classora.infrastructure.adapter.out.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toDomain(PriceEntity entity);
    PriceDto toDto(Price domain);
    PriceResponse toResponse(PriceDto dto);
}
