package com.production.hearu.auth;

public class AuthenticationResponse {// refactor to use records
    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
