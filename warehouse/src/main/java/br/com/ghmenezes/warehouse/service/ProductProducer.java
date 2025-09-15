package br.com.ghmenezes.warehouse.service;


import br.com.ghmenezes.warehouse.controller.request.ProductSaveRequest;
import br.com.ghmenezes.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.ghmenezes.warehouse.dto.StockStatusMessage;

public interface ProductProducer {

    void notifyStatusChange(final StockStatusMessage message);

    void notifyProductCreated(final ProductStorefrontSaveDTO product);
}
