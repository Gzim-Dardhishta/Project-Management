package com.example.PM.entity.DTO;

import com.example.PM.entity.Role;

import java.util.Set;

public record UserDTO(
        int id,
        String name,
        String lastName,
        String username,
        String email,
        int phoneNumber,
        String country,
        String city,
        Set<Role> roles
) {
}
