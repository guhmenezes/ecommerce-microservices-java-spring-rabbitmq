package br.com.ghmenezes.storefront.mapper;

import br.com.ghmenezes.storefront.controller.request.ProductSaveRequest;
import br.com.ghmenezes.storefront.controller.response.ProductAvailableResponse;
import br.com.ghmenezes.storefront.controller.response.ProductDetailsResponse;
import br.com.ghmenezes.storefront.controller.response.ProductSavedResponse;
import br.com.ghmenezes.storefront.dto.ProductCreatedMessage;
import br.com.ghmenezes.storefront.entity.ProductEntity;
import br.com.ghmenezes.storefront.dto.ProductInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    ProductInfoDTO toDTO(final ProductEntity entity, final BigDecimal price);

    @Mapping(target = "active", constant = "false")
    ProductEntity toEntity(final ProductSaveRequest request);

    @Mapping(target = "active", constant = "false")
    ProductEntity toEntity(final ProductCreatedMessage request);

    ProductSavedResponse toResponse(final ProductEntity entity);

    List<ProductAvailableResponse> toResponse(final List<ProductEntity> entities);

    ProductDetailsResponse toResponse(final ProductInfoDTO dto);
}
