import { Task, TaskFormData } from '../types/task'; // Import Task and TaskFormData types

const API_URL = 'http://localhost:8080/api/tasks'; // Backend API base URL

// API service object containing all HTTP methods
export const taskService = { // Define taskService object and export it
  // GET /api/tasks - Fetch all tasks
  getAll: async (): Promise<Task[]> => { // Return a promise that resolves to an array of Task
    const response = await fetch(API_URL); // Make GET request to /api/tasks
    if (!response.ok) throw new Error('Failed to fetch tasks'); // Error handling
    return response.json() as Promise<Task[]>; // Parse JSON response
  },

  // GET /api/tasks/{id} - Fetch single task by ID
  getById: async (id: number): Promise<Task> => { // Accept task ID as parameter and return a promise that resolves to a Task
    const response = await fetch(`${API_URL}/${id}`); // Make GET request to /api/tasks/{id}
    if (!response.ok) throw new Error(`Task with ID ${id} not found`); // Error handling
    return response.json() as Promise<Task>;  // Parse JSON response
  },

  // POST /api/tasks - Create new task
  create: async (task: TaskFormData): Promise<Task> => { // Accept TaskFormData and return a promise that resolves to the created Task
    const response = await fetch(API_URL, { // Make POST request to /api/tasks
      method: 'POST', 
      headers: { 'Content-Type': 'application/json' }, // Tell server we're sending JSON
      body: JSON.stringify(task), // Convert object to JSON string
    });
    if (!response.ok) throw new Error('Failed to create task'); // Error handling
    return response.json() as Promise<Task>; // Parse JSON response
  },

  // PUT /api/tasks/{id} - Update existing task
  update: async (id: number, task: Partial<TaskFormData>): Promise<Task> => { // Accept task ID and partial TaskFormData, return updated Task
    const response = await fetch(`${API_URL}/${id}`, {  // Make PUT request to /api/tasks/{id}
      method: 'PUT', // HTTP method
      headers: { 'Content-Type': 'application/json' }, // JSON content type
      body: JSON.stringify(task), // Convert updated fields to JSON string
    });
    if (!response.ok) throw new Error(`Failed to update task ${id}`); // Error handling
    return response.json() as Promise<Task>;  // Parse JSON response
  },

  // DELETE /api/tasks/{id} - Delete task
  delete: async (id: number): Promise<void> => { // Accept task ID, return void
    const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' }); // make Delete request to /api/tasks/{id}
    if (!response.ok) throw new Error(`Failed to delete task ${id}`);
  },

  // PUT /api/tasks/{id}/toggle - Toggle task completion status
  toggle: async (id: number): Promise<Task> => { // Accept task ID, return updated Task
    const response = await fetch(`${API_URL}/${id}/toggle`, { method: 'PUT' });
    if (!response.ok) throw new Error(`Failed to toggle task ${id}`);
    return response.json() as Promise<Task>;
  },

  // GET /api/tasks/search?keyword=xxx - Search tasks by title
  search: async (keyword: string): Promise<Task[]> => { // Accept search keyword, return array of matching Tasks
    const params = new URLSearchParams({ keyword }); // Build query string
    const response = await fetch(`${API_URL}/search?${params}`);
    if (!response.ok) throw new Error('Search failed');
    return response.json() as Promise<Task[]>;
  },

  // GET /api/tasks/incomplete - Fetch only incomplete tasks
  getIncomplete: async (): Promise<Task[]> => {  // Return array of incomplete Tasks
    const response = await fetch(`${API_URL}/incomplete`);
    if (!response.ok) throw new Error('Failed to fetch incomplete tasks');
    return response.json() as Promise<Task[]>;
  },
};
 // some comment: 
// This service module provides methods to interact with the backend Task API.
// Each method corresponds to a specific API endpoint and HTTP method,
// handling requests and responses using the Fetch API and TypeScript types for safety.