package com.production.heard.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User
{
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = StrategyT)
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
}
