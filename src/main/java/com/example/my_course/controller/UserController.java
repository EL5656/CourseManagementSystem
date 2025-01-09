package com.example.my_course.controller;

import com.example.my_course.dto.AuthenticationRequest;
import com.example.my_course.dto.AuthenticationResponse;
import com.example.my_course.dto.RegisterRequest;
import com.example.my_course.entity.Role;
import com.example.my_course.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("my_course_store/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register/{role}")
    public ResponseEntity<AuthenticationResponse> register(@PathVariable String role, @RequestBody RegisterRequest registerRequest) {
        System.out.println("### Received registration request with role: " + role);  // Log the role received

        if (!role.equals("user") && !role.equals("admin")) {
            System.out.println("### Invalid role received: " + role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        if ("admin".equalsIgnoreCase(role)) {
            registerRequest.setRole(Role.ADMIN);
            System.out.println("### Role set to ADMIN");
        } else if ("user".equalsIgnoreCase(role)) {
            registerRequest.setRole(Role.USER);
            System.out.println("### Role set to USER");
        }

        // Log RegisterRequest fields (For debugging purposes)
        System.out.println("### RegisterRequest: Username: " + registerRequest.getUsername());
        System.out.println("### RegisterRequest: Email: " + registerRequest.getEmail());
        System.out.println("### RegisterRequest: Mobile: " + registerRequest.getMobile());
        System.out.println("### RegisterRequest: Role: " + registerRequest.getRole());

        AuthenticationResponse response = authenticationService.register(role, registerRequest);
        // Log after the service call
        System.out.println("### After authenticationService.register call");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //todo - test this api with postman
    //todo - check security config
    @PostMapping("/authenticate/{role}")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @PathVariable String role,
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AuthenticationResponse(null, "Access denied"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //token
}
