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
    │   │   └── CorsConfig.java
    │   ├── controller/
    │   │   └── TaskController.java
    │   ├── dto/
    │   │   └── TaskDTO.java
    │   ├── entity/
    │   │   └── Task.java
    │   ├── repository/
    │   │   └── TaskRepository.java
    │   ├── service/
    │   │   └── TaskService.java
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

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get task by ID |
| POST | /api/tasks | Create task |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |
| PUT | /api/tasks/{id}/toggle | Toggle completion |
| GET | /api/tasks/search?keyword=... | Search tasks |

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

### Key Files to Study

1. **Task.java** - How database tables are defined
2. **TaskRepository.java** - How queries are created from method names
3. **TaskService.java** - Where business logic lives
4. **TaskController.java** - How REST endpoints work
5. **taskService.ts** - How frontend calls backend
6. **TasksPage.tsx** - How React state management works

---

## Next Steps: Phase 2

After studying this code, you'll build **Category CRUD**:

**Backend:**
- Category.java (Entity)
- CategoryRepository.java
- CategoryService.java
- CategoryController.java
- CategoryDTO.java

**Frontend:**
- types/category.ts
- services/categoryService.ts
- components/CategoryItem.tsx
- components/CategoryForm.tsx
- components/CategoryList.tsx
- pages/CategoriesPage.tsx

The pattern is the same - that's the point!

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
