package br.com.matteusmoreno.picpay_backend_challenge.exception;

public class EmailNotSentException extends RuntimeException{

    public EmailNotSentException() {
        super("Email not sent due to system inconsistency. Please try again later.");
    }
}
