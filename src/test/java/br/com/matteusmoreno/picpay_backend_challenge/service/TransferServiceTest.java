package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.client.EmailSenderClient;
import br.com.matteusmoreno.picpay_backend_challenge.client.TransferClient;
import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;
import br.com.matteusmoreno.picpay_backend_challenge.exception.AuthorizationDeniedException;
import br.com.matteusmoreno.picpay_backend_challenge.exception.CommonUserNotFoundException;
import br.com.matteusmoreno.picpay_backend_challenge.exception.EmailNotSentException;
import br.com.matteusmoreno.picpay_backend_challenge.exception.InsufficientFundsException;
import br.com.matteusmoreno.picpay_backend_challenge.repository.CommonUserRepository;
import br.com.matteusmoreno.picpay_backend_challenge.repository.ShopkeeperRepository;
import br.com.matteusmoreno.picpay_backend_challenge.repository.TransferRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateDepositRequest;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateTransferRequest;
import br.com.matteusmoreno.picpay_backend_challenge.response.DepositDetailsResponse;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private CommonUserRepository commonUserRepository;

    @Mock
    private ShopkeeperRepository shopkeeperRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private TransferClient transferClient;

    @Mock
    private EmailSenderClient emailSenderClient;

    @InjectMocks
    private TransferService transferService;

    //DEPOSIT
    @Test
    @DisplayName("Should update the balance of an existing CommonUser and return deposit details")
    void depositShouldUpdateBalanceAndReturnDepositDetails() {

        CommonUser commonUser = new CommonUser(1L, "Matteus Moreno", "598.478.589-98",
                "email@email.com", "123", new BigDecimal("50.0"));

        CreateDepositRequest request = new CreateDepositRequest(1L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.id())).thenReturn(Optional.of(commonUser));

        DepositDetailsResponse result = transferService.deposit(request);

        verify(commonUserRepository, times(1)).findById(request.id());
        verify(commonUserRepository, times(1)).save(commonUser);

        assertEquals(request.id(), commonUser.getId());
        assertEquals(commonUser.getEmail(), result.email());
        assertEquals(new BigDecimal("150.00"), commonUser.getBalance());
        assertEquals(new BigDecimal("150.00"), result.balance());
        assertEquals(new BigDecimal("100.00"), result.value());
    }

    @Test
    @DisplayName("Should throw CommonUserNotFoundException if user is not found")
    void depositShouldThrowExceptionIfUserNotFound() {

        CreateDepositRequest request = new CreateDepositRequest(1L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(CommonUserNotFoundException.class, () -> transferService.deposit(request));

        verify(commonUserRepository, times(1)).findById(request.id());
        verify(commonUserRepository, never()).save(any(CommonUser.class));
    }

    //TRANSFER
    @Test
    @DisplayName("Should perform a transfer successfully")
    void transferShouldBeSuccessful() {
        CommonUser payer = new CommonUser(1L, "Matteus Moreno", "598.478.589-98", "email1@email.com", "password123", new BigDecimal("200.00"));
        Shopkeeper payee = new Shopkeeper(2L, "Shopkeeper", "24.839.175/0001-55", "email2@email.com", "password123", new BigDecimal("50.00"));
        CreateTransferRequest request = new CreateTransferRequest(1L, 2L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.payer())).thenReturn(Optional.of(payer));
        when(shopkeeperRepository.findById(request.payee())).thenReturn(Optional.of(payee));

        Response transferResponse = Response.builder()
                .status(200)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, null, null))
                .build();

        Response emailResponse = Response.builder()
                .status(200)
                .request(Request.create(Request.HttpMethod.POST, "", Collections.emptyMap(), null, null, null))
                .build();

        when(transferClient.transferAuthorizer()).thenReturn(transferResponse);
        when(emailSenderClient.emailSender()).thenReturn(emailResponse);

        Transfer result = transferService.transfer(request);

        verify(commonUserRepository, times(1)).findById(request.payer());
        verify(shopkeeperRepository, times(1)).findById(request.payee());
        verify(commonUserRepository, times(1)).save(payer);
        verify(shopkeeperRepository, times(1)).save(payee);
        verify(transferRepository, times(1)).save(result);
        verify(transferClient, times(1)).transferAuthorizer();
        verify(emailSenderClient, times(1)).emailSender();

        assertEquals(request.payer(), payer.getId());
        assertEquals(request.payee(), payee.getId());
        assertEquals(new BigDecimal("100.00"), payer.getBalance());
        assertEquals(new BigDecimal("150.00"), payee.getBalance());
    }

    @Test
    @DisplayName("Should throw InsufficientFundsException when payer has insufficient funds")
    void transferShouldThrowInsufficientFundsException() {
        CommonUser payer = new CommonUser(1L, "Matteus Moreno", "598.478.589-98", "email1@email.com", "password123", new BigDecimal("50.00"));
        Shopkeeper payee = new Shopkeeper(2L, "Shopkeeper", "24.839.175/0001-55", "email2@email.com", "password123", new BigDecimal("50.00"));
        CreateTransferRequest request = new CreateTransferRequest(1L, 2L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.payer())).thenReturn(Optional.of(payer));
        when(shopkeeperRepository.findById(request.payee())).thenReturn(Optional.of(payee));

        assertThrows(InsufficientFundsException.class, () -> transferService.transfer(request));

        verify(commonUserRepository, times(1)).findById(request.payer());
        verify(shopkeeperRepository, times(1)).findById(request.payee());
    }

    @Test
    @DisplayName("Should throw AuthorizationDeniedException when authorization fails")
    void transferShouldThrowAuthorizationDeniedException() {
        CommonUser payer = new CommonUser(1L, "Matteus Moreno", "598.478.589-98", "email1@email.com", "password123", new BigDecimal("200.00"));
        Shopkeeper payee = new Shopkeeper(2L, "Shopkeeper", "24.839.175/0001-55", "email2@email.com", "password123", new BigDecimal("50.00"));
        CreateTransferRequest request = new CreateTransferRequest(1L, 2L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.payer())).thenReturn(Optional.of(payer));
        when(shopkeeperRepository.findById(request.payee())).thenReturn(Optional.of(payee));

        Response transferResponse = Response.builder()
                .status(403)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, null, null))
                .build();

        when(transferClient.transferAuthorizer()).thenReturn(transferResponse);

        assertThrows(AuthorizationDeniedException.class, () -> transferService.transfer(request));

        verify(commonUserRepository, times(1)).findById(request.payer());
        verify(shopkeeperRepository, times(1)).findById(request.payee());
        verify(transferClient, times(1)).transferAuthorizer();
    }

    @Test
    @DisplayName("Should throw EmailNotSentException when email sending fails")
    void transferShouldThrowEmailNotSentException() {
        CommonUser payer = new CommonUser(1L, "Matteus Moreno", "598.478.589-98", "email1@email.com", "password123", new BigDecimal("200.00"));
        Shopkeeper payee = new Shopkeeper(2L, "Shopkeeper", "24.839.175/0001-55", "email2@email.com", "password123", new BigDecimal("50.00"));
        CreateTransferRequest request = new CreateTransferRequest(1L, 2L, new BigDecimal("100.00"));

        when(commonUserRepository.findById(request.payer())).thenReturn(Optional.of(payer));
        when(shopkeeperRepository.findById(request.payee())).thenReturn(Optional.of(payee));

        Response transferResponse = Response.builder()
                .status(200)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, null, null))
                .build();

        Response emailResponse = Response.builder()
                .status(504)
                .request(Request.create(Request.HttpMethod.POST, "", Collections.emptyMap(), null, null, null))
                .build();

        when(transferClient.transferAuthorizer()).thenReturn(transferResponse);
        when(emailSenderClient.emailSender()).thenReturn(emailResponse);

        assertThrows(EmailNotSentException.class, () -> transferService.transfer(request));

        verify(commonUserRepository, times(1)).findById(request.payer());
        verify(shopkeeperRepository, times(1)).findById(request.payee());
        verify(transferClient, times(1)).transferAuthorizer();
        verify(emailSenderClient, times(1)).emailSender();
    }

}