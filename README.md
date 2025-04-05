# LearnHub Project

## Project Structure

- **Frontend**: React with TypeScript and Tailwind CSS
- **Backend**: Java with Javalin and MongoDB

### Prerequisites
- Node.js and npm
- Java 11 or higher
- Maven

## HOW TO RUN

- First, download ZIP or fork the entire repo and open in VS Code
- Parallely, open two terminals (one for backend and one for frontend) and follow the commands below

### Backend Setup

1. Navigate to the backend directory:
   ```
   cd backend
   ```

2. Build the project with Maven:
   ```
   mvn clean package
   ```

3. Run the backend server (PORT 8081):
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

3. Start the development server (PORT 5173):
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
