package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.exception.DuplicateResourceException;
import br.com.matteusmoreno.picpay_backend_challenge.repository.ShopkeeperRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateShopkeeperRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopkeeperServiceTest {

    @Mock
    private ShopkeeperRepository shopkeeperRepository;

    @InjectMocks
    private ShopkeeperService shopkeeperService;

    @Test
    @DisplayName("Should create a new Shopkeeper and persist it in the database")
    void shouldCreateNewShopkeeperAndPersistItInTheDatabase() {

        CreateShopkeeperRequest request = new CreateShopkeeperRequest("CASA MORENO", "24.839.175/0001-55",
                "casamoreno@email.com", "123");

        Shopkeeper shopkeeper = shopkeeperService.createShopkeeper(request);
        shopkeeper.setId(2L);

        verify(shopkeeperRepository, times(1)).save(shopkeeper);

        assertEquals(2L, shopkeeper.getId());
        assertEquals(request.completeName(), shopkeeper.getCompleteName());
        assertEquals(request.cnpj(), shopkeeper.getCnpj());
        assertEquals(request.email(), shopkeeper.getEmail());
        assertEquals(request.password(), shopkeeper.getPassword());
        assertEquals(new BigDecimal("0.0"), shopkeeper.getBalance());
    }

    @Test
    @DisplayName("Create shopkeeper with duplicate email")
    void createShopkeeperWithDuplicateEmail() {
        CreateShopkeeperRequest request = new CreateShopkeeperRequest(
                "CASA MORENO",
                "24.839.175/0001-55",
                "casamoreno@email.com",
                "123"
        );

        doThrow(new DataIntegrityViolationException("CNPJ or Email already exists")).when(shopkeeperRepository).save(any(Shopkeeper.class));

        assertThrows(DuplicateResourceException.class, () -> {
            shopkeeperService.createShopkeeper(request);
        });
    }

    @Test
    @DisplayName("Create shopkeeper with duplicate CNPJ")
    void createShopkeeperWithDuplicateCnpj() {
        CreateShopkeeperRequest request = new CreateShopkeeperRequest(
                "CASA MORENO",
                "24.839.175/0001-55",
                "casamoreno@email.com",
                "123"
        );

        doThrow(new DataIntegrityViolationException("CNPJ already exists")).when(shopkeeperRepository).save(any(Shopkeeper.class));

        assertThrows(DuplicateResourceException.class, () -> {
            shopkeeperService.createShopkeeper(request);
        });
    }
}