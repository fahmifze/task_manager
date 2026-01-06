// Task entity returned from API (matches backend DTO)
export interface Task {
  id: number; // Unique identifier
  title: string; // Task title
  description: string; // Task description
  completed: boolean; // Completion status
  createdAt: string; // ISO date string
  updatedAt: string; // ISO date string
  dueDate?: string; // ISO date string for due date (optional)
  categoryId?: number; // Category ID (optional)
  categoryName?: string; // Category name for display
  categoryColor?: string; // Category color for display
}

// Data required to create a new task
// in this interface it will have title, description and completed(optional) fields
export interface TaskFormData {
  title: string;
  description: string;
  completed?: boolean; // Optional, defaults to false
  dueDate?: string; // Optional, ISO date string for due date
  categoryId?: number; // Optional, category ID
}

// Partial form data for updates (all fields optional)
// in this type it will have all fields from TaskFormData but optional
export type TaskUpdateData = Partial<TaskFormData>;

// Filter options for task list
// in this type it will have three options: all, completed and incomplete
export type TaskStatus = 'all' | 'completed' | 'incomplete';

// Generic API response wrapper
// in this interface it will have data of generic type T, optional message and success boolean
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}
