package br.com.matteusmoreno.picpay_backend_challenge.repository;

import br.com.matteusmoreno.picpay_backend_challenge.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
