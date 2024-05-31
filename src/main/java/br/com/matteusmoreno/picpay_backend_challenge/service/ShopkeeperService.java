package br.com.matteusmoreno.picpay_backend_challenge.service;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.repository.ShopkeeperRepository;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateShopkeeperRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShopkeeperService {

    private final ShopkeeperRepository shopkeeperRepository;

    @Autowired
    public ShopkeeperService(ShopkeeperRepository shopkeeperRepository) {
        this.shopkeeperRepository = shopkeeperRepository;
    }

    @Transactional
    public Shopkeeper createShopkeeper(CreateShopkeeperRequest request) {
        Shopkeeper shopkeeper = new Shopkeeper();
        BeanUtils.copyProperties(request, shopkeeper);
        shopkeeper.setBalance(new BigDecimal("0.0"));

        shopkeeperRepository.save(shopkeeper);

        return shopkeeper;
    }
}
