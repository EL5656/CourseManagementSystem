package com.example.my_course.service;

import com.example.my_course.auth.JWTService;
import com.example.my_course.dto.AuthenticationRequest;
import com.example.my_course.dto.AuthenticationResponse;
import com.example.my_course.dto.RegisterRequest;
import com.example.my_course.entity.Role;
import com.example.my_course.entity.User;
import com.example.my_course.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JWTService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        System.out.println("### UserRepository Injected: " + userRepository);
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(String role, RegisterRequest request) {
        try {
            System.out.println("### Register method called with role: " + role);  // Log the role received

            if (request == null || request.getRole() == null) {
                throw new IllegalArgumentException("Role or registration request cannot be null");
            }

            // Log the RegisterRequest details
            System.out.println("### RegisterRequest Details: ");
            System.out.println("Username: " + request.getUsername());
            System.out.println("Email: " + request.getEmail());
            System.out.println("Mobile: " + request.getMobile());
            System.out.println("Role: " + request.getRole());

            // Create user, save to database, generate token
            var user = new User(
                    request.getUsername(),
                    request.getEmail(),
                    request.getMobile(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getRole()  // The role is already set in the RegisterRequest
            );

            var jwtToken = jwtService.generateToken(user);

            // Log the generated token
            System.out.println("### JWT Token generated: " + jwtToken);

            if (jwtToken != null) {
                userRepository.save(user);
                System.out.println("### User saved successfully");
                return new AuthenticationResponse(jwtToken, "Token generated successfully");
            } else {
                // do not save the user to the database
                throw new Exception("JWT token generation failed, user not saved");
            }

        } catch (Exception e) {
            System.out.println("### Error during registration: " + e.getMessage());
            return new AuthenticationResponse(null, "Error occurred during registration: " + e.getMessage());
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            var jwtToken = jwtService.generateToken(user);

            //JWT token generated successfully for login
            return new AuthenticationResponse(jwtToken, "Success");

        } catch (Exception e) {
            return new AuthenticationResponse(null, "Authentication failed: " + e.getMessage());
        }
    }

}
