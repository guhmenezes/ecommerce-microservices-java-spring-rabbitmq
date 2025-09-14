package br.com.ghmenezes.warehouse.service;

import br.com.ghmenezes.warehouse.entity.ProductEntity;

import java.util.UUID;

public interface ProductService {

    ProductEntity save(final ProductEntity entity);

    ProductEntity findById(final UUID id);

    void purchase(final UUID id);

}
