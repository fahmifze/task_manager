import { Task, TaskFormData } from '../types/task'; // Import Task and TaskFormData types

const API_URL = 'http://localhost:8080/api/tasks'; // Backend API base URL

// Helper function to get auth headers with JWT token
// This adds "Authorization: Bearer <token>" to every request
const getAuthHeaders = (): HeadersInit => {
  const token = localStorage.getItem('token'); // Get token from localStorage
  return {
    'Content-Type': 'application/json',
    ...(token && { Authorization: `Bearer ${token}` }), // Add token if exists
  };
};

// API service object containing all HTTP methods
export const taskService = {
  // GET /api/tasks - Fetch all tasks for current user
  getAll: async (): Promise<Task[]> => {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error('Failed to fetch tasks');
    return response.json() as Promise<Task[]>;
  },

  // GET /api/tasks/{id} - Fetch single task by ID
  getById: async (id: number): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}`, {
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error(`Task with ID ${id} not found`);
    return response.json() as Promise<Task>;
  },

  // POST /api/tasks - Create new task
  create: async (task: TaskFormData): Promise<Task> => {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: getAuthHeaders(), // Include JWT token
      body: JSON.stringify(task),
    });
    if (!response.ok) throw new Error('Failed to create task');
    return response.json() as Promise<Task>;
  },

  // PUT /api/tasks/{id} - Update existing task
  update: async (id: number, task: Partial<TaskFormData>): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: getAuthHeaders(), // Include JWT token
      body: JSON.stringify(task),
    });
    if (!response.ok) throw new Error(`Failed to update task ${id}`);
    return response.json() as Promise<Task>;
  },

  // DELETE /api/tasks/{id} - Delete task
  delete: async (id: number): Promise<void> => {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE',
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error(`Failed to delete task ${id}`);
  },

  // PUT /api/tasks/{id}/toggle - Toggle task completion status
  toggle: async (id: number): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}/toggle`, {
      method: 'PUT',
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error(`Failed to toggle task ${id}`);
    return response.json() as Promise<Task>;
  },

  // GET /api/tasks/search?keyword=xxx - Search tasks by title
  search: async (keyword: string): Promise<Task[]> => {
    const params = new URLSearchParams({ keyword });
    const response = await fetch(`${API_URL}/search?${params}`, {
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error('Search failed');
    return response.json() as Promise<Task[]>;
  },

  // GET /api/tasks/incomplete - Fetch only incomplete tasks
  getIncomplete: async (): Promise<Task[]> => {
    const response = await fetch(`${API_URL}/incomplete`, {
      headers: getAuthHeaders(), // Include JWT token
    });
    if (!response.ok) throw new Error('Failed to fetch incomplete tasks');
    return response.json() as Promise<Task[]>;
  },
};

// ============================================
// HOW JWT TOKEN IS SENT
// ============================================
//
// 1. User logs in → token stored in localStorage
//
// 2. Every API call includes header:
//    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
//
// 3. Backend JwtAuthFilter extracts and validates token
//
// 4. Backend knows which user is making the request
//
// 5. User only sees their own tasks!
