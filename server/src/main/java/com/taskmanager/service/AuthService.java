package com.taskmanager.service;

import com.taskmanager.dto.AuthRequestDTO;
import com.taskmanager.dto.AuthResponseDTO;
import com.taskmanager.entity.User;
import com.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    //Inject dependencies constructor
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    //Constructor injection
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //Register method 
    public AuthResponseDTO register(AuthRequestDTO request) {

        //Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Emails already exists");
        }

        //Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        //Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        //Hash password using passwordEncoder.encode()
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //Save user to database
        userRepository.save(user);

        //Generate token
        String token = jwtService.generateToken(user.getEmail());

        //Return AuthResponseDTO
        return new AuthResponseDTO(token, user.getUsername(), user.getEmail(), "registered");
    }

    //Login method
    public AuthResponseDTO login(AuthRequestDTO request) {

        //Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        //Check if password is correct
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        //Generate token
        String token = jwtService.generateToken(user.getEmail());

        //Return AuthResponseDTO
        return new AuthResponseDTO(token, user.getUsername(), user.getEmail(), "login");
    }
}
