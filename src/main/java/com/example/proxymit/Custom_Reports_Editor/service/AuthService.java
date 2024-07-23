//package com.example.Field_Service_App.service;
//
//import com.example.Field_Service_App.model.User_Admin;
//import com.example.Field_Service_App.repository.User_Admin_repository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private User_Admin_repository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    public String authenticate(String username, String password) {
//        User_Admin user = userRepository.findByUsernameOrEmail(username, username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
//
//        // Vérification du mot de passe
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Mot de passe incorrect");
//        }
//
//        // Génération du jeton JWT
//        return jwtTokenProvider.generateToken(user.getUsername());
//    }
//}

