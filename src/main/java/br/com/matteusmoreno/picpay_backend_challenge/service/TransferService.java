package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.client.EmailSenderClient;
import br.com.matteusmoreno.picpay_backend_challenge.client.TransferClient;
import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;
import br.com.matteusmoreno.picpay_backend_challenge.exception.*;
import br.com.matteusmoreno.picpay_backend_challenge.repository.CommonUserRepository;
import br.com.matteusmoreno.picpay_backend_challenge.repository.ShopkeeperRepository;
import br.com.matteusmoreno.picpay_backend_challenge.repository.TransferRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateDepositRequest;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateTransferRequest;
import br.com.matteusmoreno.picpay_backend_challenge.response.DepositDetailsResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final CommonUserRepository commonUserRepository;
    private final ShopkeeperRepository shopkeeperRepository;
    private final TransferRepository transferRepository;
    private final TransferClient transferClient;
    private final EmailSenderClient emailSenderClient;

    @Autowired
    public TransferService(CommonUserRepository commonUserRepository, ShopkeeperRepository shopkeeperRepository, TransferRepository transferRepository, TransferClient transferClient, EmailSenderClient emailSenderClient) {
        this.commonUserRepository = commonUserRepository;
        this.shopkeeperRepository = shopkeeperRepository;
        this.transferRepository = transferRepository;

        this.transferClient = transferClient;
        this.emailSenderClient = emailSenderClient;
    }

    @Transactional
    public DepositDetailsResponse deposit(CreateDepositRequest request) {
        CommonUser commonUser = commonUserRepository.findById(request.id()).orElseThrow(CommonUserNotFoundException::new);
        BigDecimal actualBalance = commonUser.getBalance();

        commonUser.setBalance(actualBalance.add(request.value()));

        commonUserRepository.save(commonUser);

        return new DepositDetailsResponse(commonUser.getCompleteName(), commonUser.getEmail(), request.value(), commonUser.getBalance());
    }

    @Transactional
    public Transfer transfer(CreateTransferRequest request) {
        CommonUser payer = commonUserRepository.findById(request.payer()).orElseThrow(CommonUserNotFoundException::new);
        Shopkeeper payee = shopkeeperRepository.findById(request.payee()).orElseThrow(ShopkeeperNotFoundException::new);

        if (payer.getBalance().compareTo(request.value()) < 0) {
            throw new InsufficientFundsException();
        }

        if (transferClient.transferAuthorizer().status() == 403) {
            throw new AuthorizationDeniedException();
        }

        if (emailSenderClient.emailSender().status() == 504) {
            throw new EmailNotSentException();
        }


        payer.setBalance(payer.getBalance().subtract(request.value()));
        payee.setBalance(payee.getBalance().add(request.value()));

        Transfer transfer = new Transfer();
        transfer.setPayer(payer);
        transfer.setPayee(payee);
        transfer.setValue(request.value());

        transferRepository.save(transfer);

        return transfer;
    }
}
