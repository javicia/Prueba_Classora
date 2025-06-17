package com.prueba.classora.infrastructure.persistence.mapper;

import com.prueba.classora.application.in.dto.PriceDto;
import com.prueba.classora.domain.model.Price;
import com.prueba.classora.infrastructure.adapter.in.PriceResponse;
import com.prueba.classora.infrastructure.adapter.out.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "id", source = "id")
    Price toDomain(PriceEntity entity);

    @Mapping(target = "brandId", source = "brandId")
    PriceDto toDto(Price domain);

    PriceResponse toResponse(PriceDto dto);
}
