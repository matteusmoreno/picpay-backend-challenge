package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateDepositRequest;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateTransferRequest;
import br.com.matteusmoreno.picpay_backend_challenge.response.CommonUserDetailsResponse;
import br.com.matteusmoreno.picpay_backend_challenge.response.DepositDetailsResponse;
import br.com.matteusmoreno.picpay_backend_challenge.response.TransferDetailsResponse;
import br.com.matteusmoreno.picpay_backend_challenge.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PatchMapping("/deposit")
    public ResponseEntity<DepositDetailsResponse> transfer(@RequestBody @Valid CreateDepositRequest request) {
        DepositDetailsResponse deposit = transferService.deposit(request);

        return ResponseEntity.ok(deposit);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferDetailsResponse> transfer(@RequestBody @Valid CreateTransferRequest request, UriComponentsBuilder uriBuilder) {
        Transfer transfer = transferService.transfer(request);
        URI uri = uriBuilder.path("/transfer/{id}").buildAndExpand(transfer.getId()).toUri();

        return ResponseEntity.created(uri).body(new TransferDetailsResponse(transfer));
    }
}
