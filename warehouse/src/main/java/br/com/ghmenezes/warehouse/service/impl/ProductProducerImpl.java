package br.com.ghmenezes.warehouse.service.impl;

import br.com.ghmenezes.warehouse.controller.request.ProductSaveRequest;
import br.com.ghmenezes.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.ghmenezes.warehouse.dto.StockStatusMessage;
import br.com.ghmenezes.warehouse.service.ProductProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductProducerImpl implements ProductProducer {

    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange.product-events}")
    private String exchangeName;
    @Value("${spring.rabbitmq.routing-key.product-change-availability}")
    private String changeAvailabilityRoutingKeyName;
    @Value("${spring.rabbitmq.routing-key.product-created}")
    private String createdRoutingKeyName;

    public ProductProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyStatusChange(StockStatusMessage message) {
        rabbitTemplate.convertAndSend(exchangeName, changeAvailabilityRoutingKeyName, message);
    }

    @Override
    public void notifyProductCreated(ProductStorefrontSaveDTO product) {
        rabbitTemplate.convertAndSend(exchangeName, createdRoutingKeyName, product);
    }
}
