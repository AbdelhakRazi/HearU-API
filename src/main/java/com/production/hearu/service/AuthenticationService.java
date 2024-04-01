package com.production.hearu.service;

import com.production.hearu.auth.AuthenticateRequest;
import com.production.hearu.auth.AuthenticationResponse;
import com.production.hearu.auth.RegisterRequest;
import com.production.hearu.domain.User;
import com.production.hearu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.email(),
                authenticateRequest.password()
        ));
        User user = userRepository.findByEmail(authenticateRequest.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User
                .builder()
                .firstName(registerRequest.first_name())
                .lastName(registerRequest.last_name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(registerRequest.role())
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
