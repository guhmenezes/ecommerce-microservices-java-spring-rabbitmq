package br.com.ghmenezes.storefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ProductCreatedMessage(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name
) {
}
