package br.com.matteusmoreno.picpay_backend_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "shopkeepers")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Shopkeeper {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String completeName;
    private String cnpj;
    private String email;
    private String password;
    private BigDecimal balance;
}
