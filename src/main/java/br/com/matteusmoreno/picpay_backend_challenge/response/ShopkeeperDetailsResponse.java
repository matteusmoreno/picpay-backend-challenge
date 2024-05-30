package br.com.matteusmoreno.picpay_backend_challenge.response;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;

import java.math.BigDecimal;
import java.util.UUID;

public record ShopkeeperDetailsResponse(
        UUID id,
        String completeName,
        String cnpj,
        String email,
        String password,
        BigDecimal balance) {

    public ShopkeeperDetailsResponse(Shopkeeper shopkeeper) {
        this(shopkeeper.getId(), shopkeeper.getCompleteName(), shopkeeper.getCnpj(),
                shopkeeper.getEmail(), shopkeeper.getPassword(), shopkeeper.getBalance());
    }
}
