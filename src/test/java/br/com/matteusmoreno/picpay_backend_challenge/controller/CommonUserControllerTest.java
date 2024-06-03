package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import br.com.matteusmoreno.picpay_backend_challenge.service.CommonUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CommonUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommonUserService commonUserService;

    @BeforeEach
    void setUp() {
        CommonUser commonUser = new CommonUser(1L, "Davi de Almeida Moreno", "456.789.145-98", "davi@email.com", "davi123", new BigDecimal("0.0"));
        when(commonUserService.createCommonUser(any(CreateCommonUserRequest.class))).thenReturn(commonUser);
    }

    @Test
    @DisplayName("Create common user with invalid request")
    void createCommonUserWithInvalidRequest() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/users/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Create common user with valid request")
    void createCommonUserWithValidRequest() throws Exception {
        String json = """
                {
                    "completeName" : "Davi de Almeida Moreno",
                    "cpf" : "456.789.145-98",
                    "email" : "davi@email.com",
                    "password" : "davi123"
                }
                """;

        var response = mockMvc.perform(
                post("/users/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());

    }

}