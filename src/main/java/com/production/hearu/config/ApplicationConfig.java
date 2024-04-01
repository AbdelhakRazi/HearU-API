package com.production.hearu.config;

import com.production.hearu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration // means spring will pick up this class and inject all beans declared here
public class ApplicationConfig {
    private final UserRepository userRepository;
    @Autowired
    public ApplicationConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
