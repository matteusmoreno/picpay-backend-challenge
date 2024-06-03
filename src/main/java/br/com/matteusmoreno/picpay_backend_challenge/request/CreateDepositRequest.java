package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateDepositRequest(
        @NotNull(message = "ID cannot be null")
        Long id,
        @Positive(message = "Value cannot be negative")
        @NotNull(message = "Value cannot be null")
        BigDecimal value) {
}
