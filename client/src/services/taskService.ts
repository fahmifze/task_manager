import { Task, TaskFormData } from '../types/task';

const API_URL = 'http://localhost:8080/api/tasks'; // Backend API base URL

// API service object containing all HTTP methods
export const taskService = {
  // GET /api/tasks - Fetch all tasks
  getAll: async (): Promise<Task[]> => {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error('Failed to fetch tasks');
    return response.json() as Promise<Task[]>;
  },

  // GET /api/tasks/{id} - Fetch single task by ID
  getById: async (id: number): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) throw new Error(`Task with ID ${id} not found`);
    return response.json() as Promise<Task>;
  },

  // POST /api/tasks - Create new task
  create: async (task: TaskFormData): Promise<Task> => {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }, // Tell server we're sending JSON
      body: JSON.stringify(task), // Convert object to JSON string
    });
    if (!response.ok) throw new Error('Failed to create task');
    return response.json() as Promise<Task>;
  },

  // PUT /api/tasks/{id} - Update existing task
  update: async (id: number, task: Partial<TaskFormData>): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(task),
    });
    if (!response.ok) throw new Error(`Failed to update task ${id}`);
    return response.json() as Promise<Task>;
  },

  // DELETE /api/tasks/{id} - Delete task
  delete: async (id: number): Promise<void> => {
    const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error(`Failed to delete task ${id}`);
  },

  // PUT /api/tasks/{id}/toggle - Toggle task completion status
  toggle: async (id: number): Promise<Task> => {
    const response = await fetch(`${API_URL}/${id}/toggle`, { method: 'PUT' });
    if (!response.ok) throw new Error(`Failed to toggle task ${id}`);
    return response.json() as Promise<Task>;
  },

  // GET /api/tasks/search?keyword=xxx - Search tasks by title
  search: async (keyword: string): Promise<Task[]> => {
    const params = new URLSearchParams({ keyword }); // Build query string
    const response = await fetch(`${API_URL}/search?${params}`);
    if (!response.ok) throw new Error('Search failed');
    return response.json() as Promise<Task[]>;
  },

  // GET /api/tasks/incomplete - Fetch only incomplete tasks
  getIncomplete: async (): Promise<Task[]> => {
    const response = await fetch(`${API_URL}/incomplete`);
    if (!response.ok) throw new Error('Failed to fetch incomplete tasks');
    return response.json() as Promise<Task[]>;
  },
};
