package com.lpdev.financemanagerapi.security.model.entities;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.security.model.enums.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_user")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @Length(max = 60)
    private String username;

    @Length(max = 100)
    @Email(message = "Verify the e-mail format")
    @NotBlank(message = "The e-mail cannot be null.")
    private String email;

    @NotBlank(message = "The password cannot be null.")
    private String password;

    @Length(max = 25)
    @NotBlank(message = "The firstName cannot be null.")
    private String firstName;

    @Length(max = 25)
    @NotBlank(message = "The lastName cannot be null.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "walletOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Wallet wallet;

    // cascade ALL -- if exclude the user, exclude your all transactions.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    @Builder
    public User(String username, String email, String password, String firstName, String lastName){
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN){
            return  List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            return  List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void prePersist(){
        if (this.role == null){
            this.role = UserRole.USER;
        }

        // the default username if it comes null
        if (this.username == null || this.username.isEmpty()){
            this.username = this.firstName.toLowerCase() + "." + this.lastName.toLowerCase();
        }
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }
}
