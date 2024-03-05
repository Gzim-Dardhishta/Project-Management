package com.example.PM.controllers;

import com.example.PM.entity.DTO.UserDTO;
import com.example.PM.entity.User;
import com.example.PM.payload.request.AddUserRecord;
import com.example.PM.payload.request.EditUserRecord;
import com.example.PM.payload.response.MessageResponse;
import com.example.PM.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/logged-user")
    public ResponseEntity<?> loggedUser() {
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @GetMapping("/{role}")
    public List<UserDTO> getUsersByRole(@PathVariable String role) throws BadRequestException {
        return userService.getUsersByRole(role);
    }

    @PostMapping("add-user")
    public ResponseEntity<MessageResponse> addUser(@RequestBody AddUserRecord addUser) {
        userService.addUser(addUser);
        return ResponseEntity.ok(new MessageResponse("User added successfully"));
    }

    @PutMapping("edit-user/{userId}")
    public ResponseEntity<MessageResponse> editUser(@PathVariable int userId, @RequestBody EditUserRecord editUser) {
        userService.editUser(editUser, userId);
        return ResponseEntity.ok(new MessageResponse("User added successfully"));
    }
}
