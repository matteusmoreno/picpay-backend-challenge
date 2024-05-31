package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotNull
        Long payer,
        @NotNull
        Long payee,
        @NotNull
        BigDecimal value) {
}
