package br.com.matteusmoreno.picpay_backend_challenge.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
