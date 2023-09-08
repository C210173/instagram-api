package com.sky.instagram.controller;

import com.sky.instagram.exeption.UserException;
import com.sky.instagram.model.User;
import com.sky.instagram.repository.UserRepository;
import com.sky.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);

    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException{
        Optional<User> opt= userRepo.findByEmail(auth.getName());
        if (opt.isPresent()){
            return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
