package br.com.ghmenezes.storefront.service.impl;

import br.com.ghmenezes.storefront.dto.StockStatusMessage;
import br.com.ghmenezes.storefront.service.ProductChangeAvailabilityConsumer;
import br.com.ghmenezes.storefront.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductChangeAvailabilityConsumerImpl implements ProductChangeAvailabilityConsumer {

    private final ProductService service;

    @RabbitListener(queues = "${spring.rabbitmq.queue.product-change-availability}")
    @Override
    public void receive(StockStatusMessage message) {
        service.changeActivated(message.id(), message.active());
    }
}
