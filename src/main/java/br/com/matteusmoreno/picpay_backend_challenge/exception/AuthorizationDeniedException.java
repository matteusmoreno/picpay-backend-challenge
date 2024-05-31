package br.com.matteusmoreno.picpay_backend_challenge.exception;

public class AuthorizationDeniedException extends RuntimeException{

    public AuthorizationDeniedException() {
        super("Transfer not completed due to system inconsistency. Please try again later.");
    }
}
