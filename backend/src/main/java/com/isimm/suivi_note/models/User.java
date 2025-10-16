package com.isimm.suivi_note.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="user_type")
@NoArgsConstructor
@AllArgsConstructor 
@Data
@SuperBuilder
@Table(name = "t_user")
public class User  implements UserDetails {
    
    @Id
    private String cin ;
    @NotBlank(message = "firstName is missing")
    private String firstName; 
    @NotBlank(message = "lastName is missing")
    private String lastName; 
    @NotBlank(message = "email is missing")
    @Email(message = " invalid email")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "password is missing")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() {
        return this.cin;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
