package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Shopkeeper;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateShopkeeperRequest;
import br.com.matteusmoreno.picpay_backend_challenge.response.ShopkeeperDetailsResponse;
import br.com.matteusmoreno.picpay_backend_challenge.service.ShopkeeperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/shopkeepers")
public class ShopkeeperController {

    private final ShopkeeperService shopkeeperService;

    @Autowired
    public ShopkeeperController(ShopkeeperService shopkeeperService) {
        this.shopkeeperService = shopkeeperService;
    }

    @PostMapping("/create")
    public ResponseEntity<ShopkeeperDetailsResponse> create(@RequestBody @Valid CreateShopkeeperRequest request, UriComponentsBuilder uriBuilder) {
        Shopkeeper shopkeeper = shopkeeperService.createShopkeeper(request);
        URI uri = uriBuilder.path("/shopkeepers/create/{id}").buildAndExpand(shopkeeper.getId()).toUri();

        return ResponseEntity.created(uri).body(new ShopkeeperDetailsResponse(shopkeeper));

    }
}
