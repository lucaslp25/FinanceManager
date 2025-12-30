package com.lpdev.financemanagerapi.model.entities;

import com.lpdev.financemanagerapi.model.enums.TransactionType;
import com.lpdev.financemanagerapi.security.model.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal amount;
    private String description;

    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactiontype;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "withdraw_category_id", referencedColumnName = "id")
    private WithdrawCategory withdrawCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            this.date = Instant.now();
        }
    }
}

