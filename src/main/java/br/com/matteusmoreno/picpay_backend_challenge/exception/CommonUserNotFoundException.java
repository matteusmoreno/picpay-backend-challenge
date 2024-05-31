package br.com.matteusmoreno.picpay_backend_challenge.exception;

import jakarta.persistence.EntityNotFoundException;

public class CommonUserNotFoundException extends EntityNotFoundException {

    public CommonUserNotFoundException() {
        super("Common user not found");
    }
}
