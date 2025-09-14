package br.com.ghmenezes.storefront.service;

import br.com.ghmenezes.storefront.entity.ProductEntity;
import br.com.ghmenezes.storefront.dto.ProductInfoDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductEntity save(final ProductEntity entity);

    void changeActivated(final UUID id, final boolean active);

    List<ProductEntity> findAllActive();

    ProductInfoDTO findInfo(final UUID id);

    void purchase(final UUID id);
}
