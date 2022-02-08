package com.intuit.complaints.api;

import com.intuit.complaints.core.UserService;
import com.intuit.complaints.dal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok(user);
    }
}
