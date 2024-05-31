package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotNull(message = "Payer ID cannot be null")
        Long payer,
        @NotNull(message = "Payee ID cannot be null")
        Long payee,
        @NotNull(message = "Value cannot be null")
        BigDecimal value) {
}
