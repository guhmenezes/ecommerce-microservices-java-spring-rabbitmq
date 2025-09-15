package br.com.ghmenezes.storefront.service;

import br.com.ghmenezes.storefront.dto.ProductCreatedMessage;
import br.com.ghmenezes.storefront.dto.StockStatusMessage;

public interface ProductConsumer {

    void receiveStatus(final StockStatusMessage message);

    void receiveProduct(ProductCreatedMessage product) throws Exception;
}
