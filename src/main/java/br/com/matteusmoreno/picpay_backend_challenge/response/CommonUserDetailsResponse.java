package br.com.matteusmoreno.picpay_backend_challenge.response;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;

import java.util.UUID;

public record CommonUserDetailsResponse(
        UUID id,
        String completeName,
        String cpf,
        String email,
        String password) {

    public CommonUserDetailsResponse(CommonUser commonUser) {
        this(commonUser.getId(), commonUser.getCompleteName(), commonUser.getCpf(),
                commonUser.getEmail(), commonUser.getPassword());
    }
}
