package com.biblio.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotBlank(message = "Username requis")
    @Size(min = 3, message = "Username trop court")
    private String username;
    
    @NotBlank(message = "Email requis")
    @Email(message = "Email invalide")
    private String email;
    
    @NotBlank(message = "Mot de passe requis")
    @Size(min = 6, message = "Mot de passe trop court")
    private String password;
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}