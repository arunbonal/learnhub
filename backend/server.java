import io.javalin.Javalin;
import io.javalin.http.Context;
import routes.AuthRoutes;
import backend.db;

public class server {
    private static final int PORT = 5000;
    
    public static void main(String[] args) {
        // Connect to MongoDB
        db.connect();
        
        // Create Javalin app
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(PORT);
        
        // Register routes
        AuthRoutes.register(app);
        
        System.out.println("Server running on port " + PORT);
        
        // Add shutdown hook to close MongoDB connection
        Runtime.getRuntime().addShutdownHook(new Thread(db::close));
    }
}
