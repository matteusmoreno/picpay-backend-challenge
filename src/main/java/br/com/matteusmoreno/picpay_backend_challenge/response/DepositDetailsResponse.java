package br.com.matteusmoreno.picpay_backend_challenge.response;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;

import java.math.BigDecimal;

public record DepositDetailsResponse(
        String completeName,
        String email,
        BigDecimal value,
        BigDecimal balance) {

    public DepositDetailsResponse(CommonUser commonUser, DepositDetailsResponse depositValue) {
        this(commonUser.getCompleteName(), commonUser.getEmail(), depositValue.value(), commonUser.getBalance());
    }
}
