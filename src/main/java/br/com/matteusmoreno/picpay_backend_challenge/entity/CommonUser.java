package br.com.matteusmoreno.picpay_backend_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "common_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CommonUser {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String completeName;
    private String cpf;
    private String email;
    private String password;
    private BigDecimal balance;
}
