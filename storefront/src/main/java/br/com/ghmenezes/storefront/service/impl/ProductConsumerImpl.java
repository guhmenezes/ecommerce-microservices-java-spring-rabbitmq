package br.com.ghmenezes.storefront.service.impl;

import br.com.ghmenezes.storefront.dto.ProductCreatedMessage;
import br.com.ghmenezes.storefront.dto.StockStatusMessage;
import br.com.ghmenezes.storefront.service.ProductConsumer;
import br.com.ghmenezes.storefront.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductConsumerImpl implements ProductConsumer {

    private final ProductService service;

    @RabbitListener(queues = "${spring.rabbitmq.queue.product-change-availability}")
    @Override
    public void receiveStatus(StockStatusMessage message) {
        service.changeActivated(message.id(), message.active());
    }


    @RabbitListener(queues = "${spring.rabbitmq.queue.product-created}")
    @Override
    public void receiveProduct(ProductCreatedMessage product) throws Exception {
        service.saveProductFromQueue(product);
    }
}
