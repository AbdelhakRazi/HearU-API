package com.production.hearu.service;

import com.production.hearu.auth.AuthenticateRequest;
import com.production.hearu.auth.AuthenticationResponse;
import com.production.hearu.auth.RegisterRequest;
import com.production.hearu.domain.User;
import com.production.hearu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.email(),
                authenticateRequest.password()
        ));
        var user = userRepository.findByEmail(authenticateRequest.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = new User(registerRequest.first_name(),
                registerRequest.last_name(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.role());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
