package com.lpdev.financemanagerapi.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_withdraw_category")
public class WithdrawCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 30, message = "The category name cannot have greater than 30 CHARACTERS")
    private String name;

    @OneToMany(mappedBy = "withdrawCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transaction = new ArrayList<>();
}
