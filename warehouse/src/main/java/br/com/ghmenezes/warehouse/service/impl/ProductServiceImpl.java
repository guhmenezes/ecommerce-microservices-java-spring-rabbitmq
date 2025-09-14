package br.com.ghmenezes.warehouse.service.impl;

import br.com.ghmenezes.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.ghmenezes.warehouse.dto.ProductStorefrontSavedDTO;
import br.com.ghmenezes.warehouse.entity.ProductEntity;
import br.com.ghmenezes.warehouse.mapper.ProductMapper;
import br.com.ghmenezes.warehouse.repository.ProductRepository;
import br.com.ghmenezes.warehouse.service.ProductService;
import br.com.ghmenezes.warehouse.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final StockService stockService;
    private final RestClient storefrontClient;
    private final ProductMapper mapper;

    @Override
    public ProductEntity save(ProductEntity entity) {
        repository.save(entity);
        var dto = mapper.toDTO(entity);
        saveStorefront(dto);
        return entity;
    }

    @Override
    public ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void purchase(UUID id) {
        var entity = findById(id);
        var stock = entity.decStock();
        repository.save(entity);

        if(stock.isUnavailable()){
            stockService.changeStatus(stock.getId(), stock.getStatus());
        }

    }

    private void saveStorefront(ProductStorefrontSaveDTO dto) {
        storefrontClient.post()
                .uri("/products")
                .body(dto)
                .retrieve()
                .body(ProductStorefrontSavedDTO.class);
    }
}
