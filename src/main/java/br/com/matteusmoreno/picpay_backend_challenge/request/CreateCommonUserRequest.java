package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCommonUserRequest(
        @NotBlank
        String completeName,
        @NotBlank
        String cpf,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password) {
}
