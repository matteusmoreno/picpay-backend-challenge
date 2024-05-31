package br.com.matteusmoreno.picpay_backend_challenge.repository;

import br.com.matteusmoreno.picpay_backend_challenge.entity.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {
}
