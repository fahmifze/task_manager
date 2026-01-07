# Task Manager - Learning Project

A simple full-stack task management application for learning React + Spring Boot.

## Project Structure

```
task-manager/
├── client/                     # React Frontend
│   ├── src/
│   │   ├── components/         # UI Components
│   │   │   ├── TaskItem.tsx    # Single task display
│   │   │   ├── TaskForm.tsx    # Create/edit form
│   │   │   └── TaskList.tsx    # List of tasks
│   │   ├── pages/
│   │   │   └── TasksPage.tsx   # Main page (smart component)
│   │   ├── services/
│   │   │   └── taskService.ts  # API calls
│   │   ├── types/
│   │   │   └── task.ts         # TypeScript interfaces
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── index.css
│   └── package.json
│
└── server/                     # Spring Boot Backend
    ├── src/main/java/com/taskmanager/
    │   ├── config/
    │   │   ├── CorsConfig.java       # CORS settings
    │   │   ├── SecurityConfig.java   # Spring Security config
    │   │   └── JwtAuthFilter.java    # JWT token filter
    │   ├── controller/
    │   │   ├── TaskController.java   # Task endpoints
    │   │   └── AuthController.java   # Login/register endpoints
    │   ├── dto/
    │   │   ├── TaskDTO.java          # Task data transfer
    │   │   ├── AuthRequestDTO.java   # Login/register request
    │   │   └── AuthResponseDTO.java  # Auth response with token
    │   ├── entity/
    │   │   ├── Task.java             # Task table
    │   │   └── User.java             # User table
    │   ├── repository/
    │   │   ├── TaskRepository.java   # Task queries
    │   │   └── UserRepository.java   # User queries
    │   ├── service/
    │   │   ├── TaskService.java      # Task business logic
    │   │   ├── AuthService.java      # Auth business logic
    │   │   └── JwtService.java       # JWT token operations
    │   └── TaskManagerApplication.java
    ├── src/main/resources/
    │   └── application.yml
    └── pom.xml
```

---

## Prerequisites

| Software | Version | Download |
|----------|---------|----------|
| Java JDK | 17+ | https://adoptium.net/ |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| Node.js | 18+ | https://nodejs.org/ |
| MySQL | 8+ | Use Laragon (you already have it!) |

---

## Quick Start

### Step 1: Create Database

Using Laragon (HeidiSQL or phpMyAdmin):

```sql
CREATE DATABASE task_manager;
```

### Step 2: Start Backend

```bash
cd server
mvn spring-boot:run
```

Backend runs at: **http://localhost:8080**

First run downloads dependencies (~2-3 minutes).
Tables are created automatically.

### Step 3: Start Frontend

Open new terminal:

```bash
cd client
npm install
npm run dev
```

Frontend runs at: **http://localhost:5173**

### Step 4: Open Browser

Go to http://localhost:5173 and start using the app!

---

## API Endpoints

### Task Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get task by ID |
| POST | /api/tasks | Create task |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |
| PUT | /api/tasks/{id}/toggle | Toggle completion |
| GET | /api/tasks/search?keyword=... | Search tasks |

### Authentication Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login and get JWT token |

**Register Request:**
```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "mypassword123"
}
```

**Login Request:**
```json
{
  "email": "john@example.com",
  "password": "mypassword123"
}
```

**Response (both endpoints):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john",
  "email": "john@example.com",
  "message": "registered"
}
```

---

## Understanding the Code

### Backend Flow (Spring Boot)

```
HTTP Request → Controller → Service → Repository → Database
                  ↓            ↓           ↓
              TaskController TaskService TaskRepository
                  ↓            ↓           ↓
              Handles HTTP   Business    Database
              routing        logic       operations
```

### Frontend Flow (React)

```
User Action → Component → Service → Backend API
     ↓            ↓          ↓
  Click btn   TasksPage   taskService.create()
     ↓            ↓          ↓
  onChange    useState    fetch() to Spring Boot
```

### JWT Authentication Flow

```
1. User registers/logs in
         ↓
2. Server validates credentials
         ↓
3. Server generates JWT token
         ↓
4. Client stores token (localStorage)
         ↓
5. Client sends token with every request
   Header: "Authorization: Bearer <token>"
         ↓
6. JwtAuthFilter validates token
         ↓
7. If valid → allow access
   If invalid → deny access (401)
```

### Key Files to Study

**Task CRUD:**
1. **Task.java** - How database tables are defined
2. **TaskRepository.java** - How queries are created from method names
3. **TaskService.java** - Where business logic lives
4. **TaskController.java** - How REST endpoints work
5. **taskService.ts** - How frontend calls backend
6. **TasksPage.tsx** - How React state management works

**Authentication (JWT):**
1. **User.java** - User entity with email/password
2. **AuthController.java** - Login/register endpoints
3. **AuthService.java** - Registration and login logic
4. **JwtService.java** - Token generation and validation
5. **JwtAuthFilter.java** - Intercepts requests to check tokens
6. **SecurityConfig.java** - Which endpoints need auth

---

## Completed Features

- ✅ Task CRUD (Create, Read, Update, Delete)
- ✅ Category management
- ✅ Tag management
- ✅ JWT Authentication (register/login)

## Next Steps: Phase 3

After studying this code, you can add:

**Protect endpoints:**
- Make tasks belong to specific users
- Only show user's own tasks
- Require authentication for task operations

**Frontend auth:**
- Login/Register pages
- Store JWT token
- Send token with requests
- Protected routes

---

## Troubleshooting

### "Port 8080 already in use"
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### "Unknown database 'task_manager'"
Create the database first in Laragon.

### "mvn: command not found"
Add Maven to your PATH environment variable.

### Frontend can't connect to backend
1. Check backend is running (http://localhost:8080/api/tasks)
2. Check CORS config allows frontend origin

### JWT token issues
1. Token expires after 24 hours - login again
2. Make sure to include "Bearer " prefix in Authorization header
3. Check browser localStorage for stored token
