package br.com.ghmenezes.storefront.service;

import br.com.ghmenezes.storefront.dto.StockStatusMessage;

public interface ProductChangeAvailabilityConsumer {

    void receive(final StockStatusMessage message);
}
