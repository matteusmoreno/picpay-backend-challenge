package br.com.matteusmoreno.picpay_backend_challenge.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCommonUserRequest(
        @NotBlank(message = "Complete name cannot be blank")
        String completeName,
        @NotBlank(message = "CPF cannot be blank")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF must be in the format 000.000.000-00")
        String cpf,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password) {
}
