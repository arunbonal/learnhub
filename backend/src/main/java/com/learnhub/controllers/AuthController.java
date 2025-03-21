package com.learnhub.controllers;

import com.learnhub.exceptions.AuthException;
import com.learnhub.models.User;
import com.learnhub.security.JwtTokenProvider;
import com.learnhub.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, 
                         UserService userService, 
                         JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        User user = userService.findByUsername(loginRequest.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("user", mapUserResponse(user));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            User user = userService.registerUser(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    signUpRequest.getPassword()
            );

            // Authenticate the user right after registration
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signUpRequest.getUsername(),
                            signUpRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", mapUserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    private Map<String, Object> mapUserResponse(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("roles", user.getRoles());
        userMap.put("profilePicture", user.getProfilePicture());
        return userMap;
    }

    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
        
        // Default constructor
        public LoginRequest() {
        }
        
        // Constructor with parameters
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        // Getters and setters
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class SignupRequest {
        @NotBlank
        @Size(min = 3, max = 20)
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 40)
        private String password;
        
        // Default constructor
        public SignupRequest() {
        }
        
        // Constructor with parameters
        public SignupRequest(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
        
        // Getters and setters
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}