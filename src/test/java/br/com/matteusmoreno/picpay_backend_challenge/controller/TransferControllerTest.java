package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateTransferRequest;
import br.com.matteusmoreno.picpay_backend_challenge.service.TransferService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        CommonUser commonUser = new CommonUser(1L, "Davi de Almeida Moreno", "456.789.145-98", "davi@email.com", "davi123", BigDecimal.ZERO);
        Shopkeeper shopkeeper = new Shopkeeper(1L, "CASA MORENO", "24.839.175/0001-55", "casamoreno@casamoreno.com", "123", BigDecimal.ZERO);

        Transfer transfer = new Transfer(10L, commonUser, shopkeeper, BigDecimal.TEN);
        when(transferService.transfer(any(CreateTransferRequest.class))).thenReturn(transfer);
    }

    @Test
    @DisplayName("Should Return HTTP Method Not Allowed for Deposit")
    void depositHttpMethodNotAllowed() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/deposit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        System.out.println(response.getContentType());

        assertEquals(405, response.getStatus());
    }

    @Test
    @DisplayName("Should Perform Deposit Successfully")
    void depositSuccess() throws Exception {
        String json = """
                {
                    "id" : "2",
                    "value" : 2
                }
                """;

        var response = mockMvc.perform(
                patch("/deposit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Should Return Invalid Request for Transfer")
    void transferInvalidRequest() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/transfer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }


    @Test
    @DisplayName("Should Perform Transfer Successfully")
    void transferSuccess() throws Exception {

        String json = """
                {
                    "payer" : 1,
                    "payee" : 2,
                    "value" : 1
                }
                """;

        var response = mockMvc.perform(
                post("/transfer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
    }
}