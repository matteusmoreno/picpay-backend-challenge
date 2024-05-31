package br.com.matteusmoreno.picpay_backend_challenge.exception;

import jakarta.persistence.EntityNotFoundException;

public class ShopkeeperNotFoundException extends EntityNotFoundException {

    public ShopkeeperNotFoundException() {
        super("Shopkeeper not found");
    }
}
