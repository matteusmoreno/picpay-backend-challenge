package br.com.matteusmoreno.picpay_backend_challenge.exception;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException() {
        super("Insufficient funds to complete the transfer.");
    }
}
