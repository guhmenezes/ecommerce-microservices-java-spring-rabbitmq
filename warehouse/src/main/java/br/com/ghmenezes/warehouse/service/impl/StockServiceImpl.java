package br.com.ghmenezes.warehouse.service.impl;

import br.com.ghmenezes.warehouse.dto.StockStatusMessage;
import br.com.ghmenezes.warehouse.entity.StockEntity;
import br.com.ghmenezes.warehouse.enums.StockStatus;
import br.com.ghmenezes.warehouse.repository.ProductRepository;
import br.com.ghmenezes.warehouse.repository.StockRepository;
import br.com.ghmenezes.warehouse.service.ProductChangeAvailabilityProducer;
import br.com.ghmenezes.warehouse.service.ProductService;
import br.com.ghmenezes.warehouse.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.ghmenezes.warehouse.enums.StockStatus.AVAILABLE;
import static br.com.ghmenezes.warehouse.enums.StockStatus.UNAVAILABLE;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository repository;
    private final ProductRepository productRepository;
    private final ProductChangeAvailabilityProducer producer;

    @Override
    public StockEntity save(StockEntity entity) {
        entity.setProduct(productRepository.findById(entity.getProduct().getId()).orElseThrow());
        return repository.save(entity);
    }

    @Override
    public void release(UUID id) {
        changeStatus(id, AVAILABLE);
    }

    @Override
    public void inactive(UUID id) {
        changeStatus(id, UNAVAILABLE);
    }

    @Override
    public void changeStatus(UUID id, StockStatus status) {
        var entity = repository.findById(id).orElseThrow();
        entity.setStatus(status);
        repository.save(entity);
        producer.notifyStatusChange(new StockStatusMessage(entity.getProduct().getId(), status));
    }
}
