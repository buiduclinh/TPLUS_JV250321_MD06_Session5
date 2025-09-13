package org.example.ra.controller;

import org.example.ra.model.User;
import org.example.ra.dto.UserResponse;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUserList(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.userList(page,size, Sort.by("username").descending()));
    }
    @PostMapping
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}
