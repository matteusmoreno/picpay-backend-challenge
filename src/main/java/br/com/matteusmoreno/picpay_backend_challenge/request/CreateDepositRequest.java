package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateDepositRequest(
        @NotNull(message = "ID cannot be null")
        Long id,
        @NotNull(message = "Value cannot be null")
        BigDecimal value) {
}
