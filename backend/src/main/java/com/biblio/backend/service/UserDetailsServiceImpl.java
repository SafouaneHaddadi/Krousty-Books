package com.biblio.backend.service;

import com.biblio.backend.model.User;
import com.biblio.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { //Si on veut que Spring security charge les users depuis la db
    
    @Autowired
    private UserRepository userRepository;
    
    //Spring Security demande : "Quand j'ai un username, comment je le trouve dans TA base ?", je réponds : "Cherche dans ma table users avec findByUsername()"
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;  // User implémente déjà UserDetails
    }
}