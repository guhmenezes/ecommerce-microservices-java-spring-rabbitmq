package br.com.ghmenezes.storefront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WarehouseClientConfig {

    @Bean
    RestClient warehouseClient (@Value("${warehouse.base-path}") final String basePath){
        return RestClient.create(basePath);
    }
}
