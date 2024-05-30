package br.com.matteusmoreno.picpay_backend_challenge.controller;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import br.com.matteusmoreno.picpay_backend_challenge.request.CreateCommonUserRequest;
import br.com.matteusmoreno.picpay_backend_challenge.response.CommonUserDetailsResponse;
import br.com.matteusmoreno.picpay_backend_challenge.service.CommonUserService;
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
@RequestMapping("/users")
public class CommonUserController {

    private final CommonUserService commonUserService;

    @Autowired
    public CommonUserController(CommonUserService commonUserService) {
        this.commonUserService = commonUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommonUserDetailsResponse> create(@RequestBody @Valid CreateCommonUserRequest request, UriComponentsBuilder uriBuilder) {

        CommonUser commonUser = commonUserService.createCommonUser(request);
        URI uri = uriBuilder.path("/users/create/{id}").buildAndExpand(commonUser.getId()).toUri();

        return ResponseEntity.created(uri).body(new CommonUserDetailsResponse(commonUser));
    }
}
