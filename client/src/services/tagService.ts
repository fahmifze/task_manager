import { Tag } from '../types/tag'; // Import Tag type from types

const API_URL = 'http://localhost:8080/api/tags'; // Backend API base URL for tags
// API service object containing all HTTP methods for tags
export const tagService = { // Define tagService object and export it
  // GET /api/tags - Fetch all tags
    getAll: async (): Promise<Tag[]> => { // Return a promise that resolves to an array of Tag
    const response = await fetch(API_URL); // Make GET request to /api/tags
    if (!response.ok) throw new Error('Failed to fetch tags'); // Error handling
    return response.json() as Promise<Tag[]>; // Parse JSON response
  },

  // GET /api/tags/{id} - Fetch single tag by ID
    getById: async (id: number): Promise<Tag> => { // Accept tag ID as parameter and return a promise that resolves to a Tag
    const response = await fetch(`${API_URL}/${id}`); // Make GET request to /api/tags/{id}
    if (!response.ok) throw new Error(`Tag with ID ${id} not found`); // Error handling
    return response.json() as Promise<Tag>;  // Parse JSON response
  },

  // POST /api/tags - Create new tag
    create: async (tag: { name: string }): Promise<Tag> => { // Accept TagFormData and return a promise that resolves to the created Tag
    const response = await fetch(API_URL, { // Make POST request to /api/tags
      method: 'POST',
        headers: { 'Content-Type': 'application/json' }, // Tell server we're sending JSON
        body: JSON.stringify(tag), // Convert object to JSON string
    });
    if (!response.ok) throw new Error('Failed to create tag'); // Error handling
    return response.json() as Promise<Tag>; // Parse JSON response
  },

  // PUT /api/tags/{id} - Update existing tag
    update: async (id: number, tag: { name: string }): Promise<Tag> => { // Accept tag ID and TagFormData, return a promise that resolves to the updated Tag
    const response = await fetch(`${API_URL}/${id}`, { // Make PUT request to /api/tags/{id}
      method: 'PUT',
        headers: { 'Content-Type': 'application/json' }, // Tell server we're sending JSON
        body: JSON.stringify(tag), // Convert object to JSON string
    });
    if (!response.ok) throw new Error(`Failed to update tag with ID ${id}`); // Error handling
    return response.json() as Promise<Tag>; // Parse JSON response
  },
    // DELETE /api/tags/{id} - Delete tag
    delete: async (id: number): Promise<void> => { // Accept tag ID and return a promise that resolves to void
    const response = await fetch(`${API_URL}/${id}`, { // Make DELETE request to /api/tags/{id}
      method: 'DELETE',
    });
    if (!response.ok) throw new Error(`Failed to delete tag with ID ${id}`); // Error handling
  },
};
