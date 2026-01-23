package com.lpdev.financemanagerapi.model.entities;

import com.lpdev.financemanagerapi.security.model.entities.User;
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
@Table(name = "tb_deposit_category")
public class DepositCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 30, message = "The category name cannot have greater than 30 CHARACTERS")
    private String name;

    @OneToMany(mappedBy = "depositCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transaction = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "DepositCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}
