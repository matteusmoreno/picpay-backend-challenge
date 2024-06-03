package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateShopkeeperRequest;
import br.com.matteusmoreno.picpay_backend_challenge.service.ShopkeeperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ShopkeeperControllerTest {

    @MockBean
    private ShopkeeperService shopkeeperService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        Shopkeeper shopkeeper = new Shopkeeper(1L, "CASA MORENO", "24.839.175/0001-55",
                "casamoreno@casamoreno.com", "123", BigDecimal.ZERO);
        when(shopkeeperService.createShopkeeper(any(CreateShopkeeperRequest.class))).thenReturn(shopkeeper);
    }

    @Test
    @DisplayName("Create shopkeepers with invalid request")
    void createShopkeeperWithInvalidRequest() throws Exception{

        String json = "{}";

        var response = mockMvc.perform(
                post("/shopkeepers/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Create shopkeepers with valid request")
    void createShopkeeperWithValidRequest() throws Exception{

        String json = """
                {
                    "completeName" : "VIA VAREJO",
                    "cnpj" : "87.125.000/0001-55",
                    "email" : "viavarejo@email.com",
                    "password" : "viavarejo123"
                }
                """;

        var response = mockMvc.perform(
                post("/shopkeepers/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
    }
}