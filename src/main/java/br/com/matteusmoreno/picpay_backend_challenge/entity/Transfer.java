package br.com.matteusmoreno.picpay_backend_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transfers")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Transfer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private CommonUser payer;
    @ManyToOne
    @JoinColumn(name = "payee_id")
    private Shopkeeper payee;
    private BigDecimal value;
}
