# LearnHub Project

A learning platform with user authentication.

## Project Structure

- **Frontend**: React with TypeScript and Tailwind CSS
- **Backend**: Java with Javalin and MongoDB

## Setup Instructions

### Prerequisites
- Node.js and npm
- Java 11 or higher
- Maven
- MongoDB Atlas account

### Backend Setup

1. Navigate to the backend directory:
   ```
   cd backend
   ```

2. Update the MongoDB connection string in `db.java` with your Atlas connection details:
   ```java
   String connectionString = "mongodb+srv://<username>:<password>@cluster0.mongodb.net/learnhub";
   ```

3. Build the project with Maven:
   ```
   mvn clean package
   ```

4. Run the backend server:
   ```
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm run dev
   ```

## Features

- User registration and authentication
- JWT-based authentication
- Secure password hashing
- MongoDB Atlas integration

## API Endpoints

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login a user
- `GET /api/user/profile` - Get user profile (protected route)

## MongoDB Schema

### User Collection
- `_id`: ObjectId
- `username`: String (unique)
- `password`: String (hashed)
- `email`: String
- `createdAt`: String 
