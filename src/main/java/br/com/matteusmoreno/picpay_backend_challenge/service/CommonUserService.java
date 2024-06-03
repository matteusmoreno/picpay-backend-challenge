package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.exception.DuplicateResourceException;
import br.com.matteusmoreno.picpay_backend_challenge.repository.CommonUserRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommonUserService {

    private final CommonUserRepository commonUserRepository;

    @Autowired
    public CommonUserService(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Transactional
    public CommonUser createCommonUser(CreateCommonUserRequest request) {
        try {
            CommonUser commonUser = new CommonUser();
            BeanUtils.copyProperties(request, commonUser);
            commonUser.setBalance(new BigDecimal("0.0"));

            commonUserRepository.save(commonUser);
            return commonUser;

        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("CPF or Email already exists");
        }
    }
}
