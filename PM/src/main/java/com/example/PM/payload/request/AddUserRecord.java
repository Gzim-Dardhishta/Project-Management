package com.example.PM.payload.request;

import java.util.Set;

public record AddUserRecord(
        String name,
        String lastName,
        String username,
        String password,
        String email,
        int phoneNumber,
        String country,
        String city,
        Set<String> roles
){}
