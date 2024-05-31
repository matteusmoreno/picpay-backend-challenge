package br.com.matteusmoreno.picpay_backend_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PicpayBackendChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayBackendChallengeApplication.class, args);
	}

}
