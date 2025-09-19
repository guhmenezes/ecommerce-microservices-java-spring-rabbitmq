package br.com.ghmenezes.storefront.service.impl;

import br.com.ghmenezes.storefront.dto.ProductCreatedMessage;
import br.com.ghmenezes.storefront.entity.ProductEntity;
import br.com.ghmenezes.storefront.dto.ProductDetailsDTO;
import br.com.ghmenezes.storefront.dto.ProductInfoDTO;
import br.com.ghmenezes.storefront.exception.ProductAlreadyExistsException;
import br.com.ghmenezes.storefront.mapper.ProductMapper;
import br.com.ghmenezes.storefront.repository.ProductRepository;
import br.com.ghmenezes.storefront.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final RestClient warehouseClient;
    private final ProductMapper mapper;

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductEntity save(ProductEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void saveProductFromQueue(ProductCreatedMessage product) {
        try {
            if (repository.existsById(product.id())) {
                throw new ProductAlreadyExistsException("Produto já existente: " + product.id());
            }

            var entity = mapper.toEntity(product);
            repository.save(entity);
            log.info("Produto recebido do RabbitMQ e salvo: ID={}, Nome={}", product.id(), product.name());
        } catch (Exception e){
            log.error("Erro ao persistir produto: ID={}, Nome={}", product.id(), product.name());
            throw e;
        }
    }

    @Override
    public void changeActivated(UUID id, boolean active) {
        ProductEntity entity = findById(id);
        entity.setActive(active);
        repository.save(entity);
    }

    @Override
    public List<ProductEntity> findAllActive() {
        return repository.findByActiveTrueOrderByNameAsc();
    }

    @Override
    public ProductInfoDTO findInfo(UUID id) {
        ProductEntity entity = findById(id);
        var price = requestCurrentAmount(id);
        return mapper.toDTO(entity, price);
    }


    @Override
    public void purchase(UUID id) {
        purchaseWarehouse(id);
    }

    private ProductEntity findById(final UUID id){
        return repository.findById(id).orElseThrow();
    }

    private BigDecimal requestCurrentAmount(final UUID id) {
        var dto = warehouseClient.get()
                .uri("/products/" + id)
                .retrieve()
                .body(ProductDetailsDTO.class);
        return dto.price();
    }

    private void purchaseWarehouse(final UUID id){
        var path = String.format("/products/%s/purchase", id);
        warehouseClient.post()
                .uri(path)
                .retrieve()
                .toBodilessEntity();
    }

}
