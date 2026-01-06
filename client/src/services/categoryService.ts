import { Category, CategoryFormData } from "../types/category"; // Import Category and CategoryFormData types
import { ApiResponse } from "../types/category"; // Import ApiResponse type

const API_URL = 'http://localhost:8080/api/categories'; // Backend API base URL

// API service object containing all HTTP methods for categories
export const categoryService = { // Define categoryService object and export it
  // GET /api/categories - Fetch all categories
  getAll: async (): Promise<Category[]> => { // Return a promise that resolves to an array of Category
    const response = await fetch(API_URL); // Make GET request to /api/categories
    if (!response.ok) throw new Error('Failed to fetch categories'); // Error handling
    return response.json() as Promise<Category[]>; // Parse JSON response
  },
    // POST /api/categories - Create new category
    create: async (category: CategoryFormData): Promise<Category> => { // Accept CategoryFormData and return a promise that resolves to the created Category
    const response = await fetch(API_URL, { // Make POST request to /api/categories
      method: 'POST', 
      headers: { 'Content-Type': 'application/json' }, // Tell server we're sending JSON
        body: JSON.stringify(category), // Convert object to JSON string
    });
    if (!response.ok) throw new Error('Failed to create category'); // Error handling
    return response.json() as Promise<Category>; // Parse JSON response
  },
    // DELETE /api/categories/{id} - Delete category
    delete: async (id: number): Promise<void> => { // Accept category ID, return void
    const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' }); // make Delete request to /api/categories/{id}
    if (!response.ok) throw new Error(`Failed to delete category ${id}`);
  },
  // PUT /api/categories/{id} - Update category
update: async (id: number, category: CategoryFormData): Promise<Category> => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(category),
  });
  if (!response.ok) throw new Error(`Failed to update category ${id}`);
  return response.json() as Promise<Category>;
},

};