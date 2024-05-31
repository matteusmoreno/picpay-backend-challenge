package br.com.matteusmoreno.picpay_backend_challenge.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "TransferClient", url = "https://util.devi.tools/api/v2/authorize")
public interface TransferClient {

    @GetMapping
    @ResponseBody
    Response transferAuthorizer();

}
