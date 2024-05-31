package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateShopkeeperRequest(
        @NotBlank(message = "Complete name cannot be blank")
        String completeName,
        @NotBlank(message = "CNPJ cannot be blank")
        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ must be in the format 00.000.000/0000-00")
        String cnpj,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password) {
}
