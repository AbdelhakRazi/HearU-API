package com.production.hearu.auth;

import com.production.hearu.domain.Role;

public record RegisterRequest (String first_name, String last_name, String email, String password, Role role){}
