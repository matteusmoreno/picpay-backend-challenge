package br.com.matteusmoreno.picpay_backend_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "shopkeepers")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Shopkeeper {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String completeName;
    private String cnpj;
    private String email;
    private String password;
}
