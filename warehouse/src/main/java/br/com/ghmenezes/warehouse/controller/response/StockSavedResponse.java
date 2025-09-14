package br.com.ghmenezes.warehouse.controller.response;

import br.com.ghmenezes.warehouse.enums.StockStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record StockSavedResponse(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("amount")
        Long amount,
        @JsonProperty("boughtPrice")
        BigDecimal boughtPrice,
        @JsonProperty("status")
        StockStatus status,
        @JsonProperty("soldPrice")
        BigDecimal soldPrice,
        @JsonProperty("productId")
        UUID productId,
        @JsonProperty("productName")
        String productName
) {
}
