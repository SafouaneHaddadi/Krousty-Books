package com.biblio.backend.controller;

import com.biblio.backend.dto.LoginRequest;
import com.biblio.backend.dto.RegisterRequest;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.UserRepository;
import com.biblio.backend.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());

        //Customiser la réponse pr ne pas afficher le mdp
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Connexion réussie");
        response.put("username", user.getUsername());
        response.put("userId", user.getId()); 

        return ResponseEntity.ok(response); //on retourne la réponse 
    }

   @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
    // Si @Valid échoue → GlobalExceptionHandler s'en occupe
    // Si service lance exception → erreur 500 pour l'instant

    User user = userService.register(request);

    //Customiser la réponse JSON (pour ne pas afficher le mot de passe)
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Compte créé avec succès");
    response.put("username", user.getUsername());  
    response.put("userId", user.getId());          
    
    // Retourne HTTP 200 OK avec le JSON
    return ResponseEntity.ok(response);

}

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

///