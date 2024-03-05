package com.example.PM.services;

import com.example.PM.entity.DTO.UserDTO;
import com.example.PM.entity.DTO.UserDTOMapper;
import com.example.PM.entity.ERole;
import com.example.PM.entity.Role;
import com.example.PM.entity.User;
import com.example.PM.exeptions.DuplicateResourceException;
import com.example.PM.exeptions.RequestValidationException;
import com.example.PM.exeptions.ResourceNotFoundException;
import com.example.PM.payload.request.AddUserRecord;
import com.example.PM.payload.request.EditUserRecord;
import com.example.PM.repository.RoleRepository;
import com.example.PM.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll().stream()
                .map(userDTOMapper)
                .toList();
    }

    public UserDTO getUser(int userId) {
        return userRepository.findById(userId).map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id %s".formatted(userId)));
    }

    public List<UserDTO> getUsersByRole(String role) throws ResourceNotFoundException {
        return switch (role) {
            case "prof" -> getAllUsers().stream()
                    .filter(user -> user.roles().contains(roleRepository.findByName(ERole.ROLE_PROFESSOR).orElseThrow()))
                    .collect(Collectors.toList());
            case "student" -> getAllUsers().stream()
                    .filter(user -> user.roles().contains(roleRepository.findByName(ERole.ROLE_STUDENT).orElseThrow()))
                    .collect(Collectors.toList());
            case "admin" -> getAllUsers().stream()
                    .filter(user -> user.roles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow()))
                    .collect(Collectors.toList());
            default -> throw new ResourceNotFoundException("No users found!! Role must be: prof || student || admin");
        };
    }

    public Optional<UserDetails> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.ofNullable((UserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public void addUser(@RequestBody AddUserRecord addUser) {

        String username = addUser.username();
        String email = addUser.email();
        if(email.isEmpty()) {
            throw new RequestValidationException("Email cannot be empty!!!");
        }
        if(username.isEmpty()) {
            throw new RequestValidationException("Username cannot be empty!!!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Error: Email is already taken!");
        }

        User newUser = new User(
                addUser.name(),
                addUser.lastName(),
                addUser.username(),
                addUser.email(),
                passwordEncoder.encode(addUser.password()),
                addUser.phoneNumber(),
                addUser.country(),
                addUser.city()
        );

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        roles.add(userRole);

        newUser.setRoles(roles);

        userRepository.save(newUser);
    }

    public void editUser(EditUserRecord editUser, int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id %s".formatted(userId)));

        String username = editUser.username();
        String email = editUser.email();
        if(email.isEmpty()) {
            throw new RequestValidationException("Email cannot be empty!!!");
        }
        if(username.isEmpty()) {
            throw new RequestValidationException("Username cannot be empty!!!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Error: Email is already taken!");
        }

        user.setName(editUser.name());
        user.setLastName(editUser.lastName());
        user.setEmail(editUser.email());
        user.setPhoneNumber(editUser.phoneNumber());
        user.setCountry(editUser.country());
        user.setCity(editUser.city());

        userRepository.save(user);
    }
}
