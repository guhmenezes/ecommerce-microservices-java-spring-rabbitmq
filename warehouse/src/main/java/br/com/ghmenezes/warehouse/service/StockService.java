package br.com.ghmenezes.warehouse.service;

import br.com.ghmenezes.warehouse.entity.StockEntity;
import br.com.ghmenezes.warehouse.enums.StockStatus;

import java.util.UUID;

public interface StockService {

    StockEntity save(final StockEntity entity);

    void release(final UUID id);

    void inactive(final UUID id);

    void changeStatus(final UUID id, final StockStatus status);

}
