package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.exception.DuplicateResourceException;
import br.com.matteusmoreno.picpay_backend_challenge.repository.CommonUserRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonUserServiceTest {

    @Mock
    private CommonUserRepository commonUserRepository;

    @InjectMocks
    private CommonUserService commonUserService;

    private Validator validator;

    @Test
    @DisplayName("Should create a new CommonUser and persist it in the database")
    void shouldCreateNewCommonUserAndPersistItInTheDatabase() {

        CreateCommonUserRequest request = new CreateCommonUserRequest("Joe Moreno", "140.874.458-98",
                "email@email.com", "123");

        CommonUser result = commonUserService.createCommonUser(request);
        result.setId(10L);

        verify(commonUserRepository, times(1)).save(any(CommonUser.class));

        assertEquals(10L, result.getId());
        assertEquals(request.completeName(), result.getCompleteName());
        assertEquals(request.cpf(), result.getCpf());
        assertEquals(request.email(), result.getEmail());
        assertEquals(request.password(), result.getPassword());
        assertEquals(new BigDecimal("0.0"), result.getBalance());
    }

    @Test
    @DisplayName("Create common user with duplicate email")
    void createCommonUserWithDuplicateEmail() {
        CreateCommonUserRequest request = new CreateCommonUserRequest("Davi de Almeida Moreno", "456.789.145-98", "davi@email.com", "davi123");

        doThrow(new DataIntegrityViolationException("CPF or Email already exists")).when(commonUserRepository).save(any(CommonUser.class));

        assertThrows(DuplicateResourceException.class, () -> {
            commonUserService.createCommonUser(request);
        });
    }

    @Test
    @DisplayName("Create common user with duplicate cpf")
    void createCommonUserWithDuplicateCpf() {
        CreateCommonUserRequest request = new CreateCommonUserRequest("Davi de Almeida Moreno", "456.789.145-98", "davi@email.com", "davi123");

        doThrow(new DataIntegrityViolationException("CPF or Email already exists")).when(commonUserRepository).save(any(CommonUser.class));

        assertThrows(DuplicateResourceException.class, () -> {
            commonUserService.createCommonUser(request);
        });
    }

}