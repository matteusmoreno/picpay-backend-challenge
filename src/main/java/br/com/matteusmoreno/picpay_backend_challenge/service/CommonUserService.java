package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.repository.CommonUserRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonUserService {

    private final CommonUserRepository commonUserRepository;

    @Autowired
    public CommonUserService(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Transactional
    public CommonUser createCommonUser(CreateCommonUserRequest request) {
        CommonUser commonUser = new CommonUser();
        BeanUtils.copyProperties(request, commonUser);

        commonUserRepository.save(commonUser);

        return commonUser;
    }
}
