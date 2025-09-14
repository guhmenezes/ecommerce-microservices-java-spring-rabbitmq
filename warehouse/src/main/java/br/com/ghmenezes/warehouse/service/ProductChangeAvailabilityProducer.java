package br.com.ghmenezes.warehouse.service;


import br.com.ghmenezes.warehouse.dto.StockStatusMessage;

public interface ProductChangeAvailabilityProducer {

    void notifyStatusChange(final StockStatusMessage message);
}
