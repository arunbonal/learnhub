package models;

import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private String createdAt;
    
    // Constructor for creating a new user
    public User(String username, String password, String email) {
        this.username = username;
        // Hash the password before storing
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }
    
    // Constructor for fetching user from database
    public User(ObjectId id, String username, String password, String email, String createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
    
    // Convert User to Document for MongoDB
    public Document toDocument() {
        Document doc = new Document();
        doc.append("username", username);
        doc.append("password", password);
        doc.append("email", email);
        doc.append("createdAt", createdAt);
        return doc;
    }
    
    // Create User from Document
    public static User fromDocument(Document doc) {
        return new User(
            doc.getObjectId("_id"),
            doc.getString("username"),
            doc.getString("password"),
            doc.getString("email"),
            doc.getString("createdAt")
        );
    }
    
    // Verify password
    public boolean verifyPassword(String passwordAttempt) {
        return BCrypt.checkpw(passwordAttempt, this.password);
    }
    
    // Save user to database
    public void save() {
        MongoCollection<Document> users = db.getDatabase().getCollection("users");
        users.insertOne(this.toDocument());
    }
    
    // Find user by username
    public static User findByUsername(String username) {
        MongoCollection<Document> users = db.getDatabase().getCollection("users");
        Document query = new Document("username", username);
        Document result = users.find(query).first();
        
        if (result == null) {
            return null;
        }
        
        return fromDocument(result);
    }
    
    // Getters and setters
    public ObjectId getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
} 