import { Category, CategoryFormData } from '../types/category';
import { useState, useEffect } from 'react';

// Props interface for CategoryForm component
interface CategoryFormProps {
  category?: Category;
  onSubmit: (category: Category) => void;
  onCancel: () => void;
}

// Form component for creating and editing categories
export function CategoryForm({ category, onSubmit, onCancel }: CategoryFormProps) {
  const [name, setName] = useState(''); // Name input state
  const isEditing = !!category; // Check if in edit mode
    // Populate form when editing, clear when creating
    useEffect(() => {
    if (category) {
      setName(category.name);
    } else {
      setName('');
    }
    }, [category]); // Re-run when category changes
    // Handle form submission
    const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent page reload
    if (!name.trim()) {
        alert('Name is required');
        return;
    }
    const formData: CategoryFormData = {
      name: name.trim(),
    };
    onSubmit({ ...category, ...formData } as Category);
    if (!category) { // Clear form only when creating new category
      setName('');
    }
    };
    // Handle cancel button click
    const handleCancel = () => {
    setName('');
    onCancel(); // Call onCancel
    };
    return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-2xl font-semibold mb-4">
        {isEditing ? 'Edit Category' : 'New Category'}
        </h2>
        <div className="mb-4">
        <label className="block text-gray-700 font-medium mb-2" htmlFor="name">
          Category Name
        </label>
        <input
          type="text"
          id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter category name"
        />
        </div>
        <div className="flex justify-end gap-3">
        <button
          type="button"
          onClick={handleCancel}
            className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-300 focus:ring-offset-2"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-offset-2"
        >
          {isEditing ? 'Update Category' : 'Create Category'}
        </button>
        </div>
    </form>
  );
}