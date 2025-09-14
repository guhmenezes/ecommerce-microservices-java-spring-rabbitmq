package br.com.ghmenezes.warehouse.mapper;

import br.com.ghmenezes.warehouse.controller.request.ProductSaveRequest;
import br.com.ghmenezes.warehouse.controller.response.ProductDetailsResponse;
import br.com.ghmenezes.warehouse.controller.response.ProductSavedResponse;
import br.com.ghmenezes.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.ghmenezes.warehouse.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "price", ignore = true)
    ProductEntity toEntity(final ProductSaveRequest request);

    ProductSavedResponse toSavedResponse(final ProductEntity entity);

    ProductStorefrontSaveDTO toDTO(final ProductEntity entity);

    ProductDetailsResponse toDetailResponse(final ProductEntity entity);
}
