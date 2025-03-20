package controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.User;
import org.bson.Document;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final byte[] JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    private static final long JWT_EXPIRATION = 86400000; // 24 hours
    
    public static void register(Context ctx) {
        try {
            // Parse request body
            Document requestBody = Document.parse(ctx.body());
            String username = requestBody.getString("username");
            String password = requestBody.getString("password");
            String email = requestBody.getString("email");
            
            // Validate input
            if (username == null || password == null || email == null) {
                ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("error", "Username, password, and email are required"));
                return;
            }
            
            // Check if user already exists
            User existingUser = User.findByUsername(username);
            if (existingUser != null) {
                ctx.status(HttpStatus.CONFLICT).json(Map.of("error", "Username already taken"));
                return;
            }
            
            // Create and save new user
            User newUser = new User(username, password, email);
            newUser.save();
            
            // Generate JWT token
            String token = generateJwtToken(username);
            
            // Return success response with token
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                "username", username,
                "email", email
            ));
            
            ctx.status(HttpStatus.CREATED).json(response);
            
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("error", e.getMessage()));
        }
    }
    
    public static void login(Context ctx) {
        try {
            // Parse request body
            Document requestBody = Document.parse(ctx.body());
            String username = requestBody.getString("username");
            String password = requestBody.getString("password");
            
            // Validate input
            if (username == null || password == null) {
                ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("error", "Username and password are required"));
                return;
            }
            
            // Find user by username
            User user = User.findByUsername(username);
            if (user == null) {
                ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Invalid credentials"));
                return;
            }
            
            // Verify password
            if (!user.verifyPassword(password)) {
                ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Invalid credentials"));
                return;
            }
            
            // Generate JWT token
            String token = generateJwtToken(username);
            
            // Return success response with token
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                "username", user.getUsername(),
                "email", user.getEmail()
            ));
            
            ctx.json(response);
            
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("error", e.getMessage()));
        }
    }
    
    public static void getProfile(Context ctx) {
        try {
            // Extract username from JWT token (this would be done in middleware)
            String username = ctx.attribute("username");
            
            // Find user by username
            User user = User.findByUsername(username);
            if (user == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(Map.of("error", "User not found"));
                return;
            }
            
            // Return user data (excluding password)
            ctx.json(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "createdAt", user.getCreatedAt()
            ));
            
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("error", e.getMessage()));
        }
    }
    
    private static String generateJwtToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(JWT_SECRET))
            .compact();
    }
} 