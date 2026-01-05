// Task entity returned from API (matches backend DTO)
export interface Task {
  id: number;
  title: string;
  description: string;
  completed: boolean;
  createdAt: string; // ISO date string
  updatedAt: string; // ISO date string
}

// Data required to create a new task
export interface TaskFormData {
  title: string;
  description: string;
  completed?: boolean; // Optional, defaults to false
}

// Partial form data for updates (all fields optional)
export type TaskUpdateData = Partial<TaskFormData>;

// Filter options for task list
export type TaskStatus = 'all' | 'completed' | 'incomplete';

// Generic API response wrapper
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}
