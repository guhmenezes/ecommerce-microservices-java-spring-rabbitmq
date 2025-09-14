package br.com.ghmenezes.warehouse.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ProductSavedResponse(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name
) {
}
