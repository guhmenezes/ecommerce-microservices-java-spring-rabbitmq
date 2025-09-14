package br.com.ghmenezes.warehouse.dto;

import br.com.ghmenezes.warehouse.enums.StockStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record StockStatusMessage(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("status")
        StockStatus status
) {
}
