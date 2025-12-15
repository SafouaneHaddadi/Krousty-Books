package com.biblio.backend.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements UserDetails  { // Spring Security dit : "Montre-moi à quoi ressemble un user". UserDetails = contrat obligatoire

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    @Column(name = "is_admin") 
    @JsonIgnore
    private boolean admin = false;
    
    public User() {}
    
    public User(Long id, String username, String email, String password, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }


     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Quels pouvoirs a cet user ?
        // Si admin = true → ROLE_ADMIN, sinon ROLE_USER
        String role = admin ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(role));
    }

    //Le compte n'a pas expiré
    @Override
    public boolean isAccountNonExpired() { return true; }
    
    //Le compte n'est pas bloqué
    @Override
    public boolean isAccountNonLocked() { return true; }
    
    //Le mdp n'a pas expiré
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    
    @Override
    public boolean isEnabled() { return true; }
}