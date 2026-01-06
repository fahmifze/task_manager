//Category entity returned from API (matches backend DTO)
export interface Category {
  id: number; // Unique identifier
  name: string; // Category name
  color: string; // Hex color code
  description: string; // Optional description
  createdAt: string; // ISO date string
  updatedAt: string; // ISO date string
}

// Data required to create a new category
export interface CategoryFormData {
  name: string;
  color: string;
  description: string;
}
// Generic API response wrapper
// in this interface it will have data of generic type T, optional message and success boolean
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}   

// Filter options for category list
// in this type it will have two options: all and byName
export type CategoryFilter = 'all' | 'byName';
