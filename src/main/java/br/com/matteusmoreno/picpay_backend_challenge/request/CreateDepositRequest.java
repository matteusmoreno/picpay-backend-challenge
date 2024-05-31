package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateDepositRequest(
        @NotNull
        Long id,
        @NotNull
        BigDecimal value) {
}
