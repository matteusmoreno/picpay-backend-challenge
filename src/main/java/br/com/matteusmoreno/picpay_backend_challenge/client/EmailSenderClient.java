package br.com.matteusmoreno.picpay_backend_challenge.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "EmailSender", url = "https://util.devi.tools/api/v1/notify)")
public interface EmailSenderClient {

    @PostMapping
    @ResponseBody
    Response emailSender();
}
