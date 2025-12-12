package com.lpdev.financemanagerapi.model.entities;

import com.lpdev.financemanagerapi.security.model.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_wallet")
@Entity
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User walletOwner;

    public void addBalance(BigDecimal amount){
        balance = balance.add(amount);
    }
    public void decreaseBalance(BigDecimal amount){
        balance = balance.subtract(amount);
    }

}
