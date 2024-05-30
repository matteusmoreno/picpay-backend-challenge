package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateShopkeeperRequest(
        @NotBlank
        String completeName,
        @NotBlank
        String cnpj,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password) {
}
