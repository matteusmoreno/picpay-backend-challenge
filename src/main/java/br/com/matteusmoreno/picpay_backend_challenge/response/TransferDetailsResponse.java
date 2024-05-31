package br.com.matteusmoreno.picpay_backend_challenge.response;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferDetailsResponse(
        Long payer,
        Long payee,
        BigDecimal value) {

    public TransferDetailsResponse(Transfer transfer) {
        this(transfer.getPayer().getId(), transfer.getPayee().getId(), transfer.getValue());
    }
}
