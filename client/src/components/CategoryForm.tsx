import { Category, CategoryFormData } from "../types/category";
import { useState, useEffect } from "react";

// Props interface for CategoryForm component
interface CategoryFormProps {
  category?: Category;
  onSubmit: (formdata : CategoryFormData) => void;
  onCancel: () => void;
}

// Form component for creating and editing categories
export function CategoryForm({ category, onSubmit, onCancel }: CategoryFormProps) {
  const [name, setName] = useState(""); // Name input state
  const isEditing = !!category; // Check if in edit mode
  const [color, setColor] = useState("#3B82F6"); // Color input state
  const [description, setDescription] = useState(""); // Description input state

  // Populate form when editing, clear when creating
  useEffect(() => {
    if (category) {
    setName(category.name);
    setColor(category.color || "#3B82F6");
    setDescription(category.description || "");
  } else {
    setName("");
    setColor("#3B82F6");
    setDescription("");
  }
  }, [category]); // Re-run when category changes
    // Handle form submission
    const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent page reload
    if (!name.trim()) {
      alert("Name is required");
      return;
    }
    const formData: CategoryFormData = {
      name: name.trim(),
      color: color,
      description: description.trim(),
    };
    onSubmit(formData); // Call onSubmit with form data
    if (!category) { // Clear form only when creating new category
      setName("");
      setColor("#3B82F6");
      setDescription("");
    }
    };
    // Handle cancel button click
    const handleCancel = () => {
    setName("");
    onCancel(); // Call onCancel
    };
    return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-semibold mb-4">
        {isEditing ? "Edit Category" : "New Category"}
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
      <div className="mb-4">
        <label className="block text-gray-700 font-medium mb-2" htmlFor="color">
          Category Color
        </label>
        <input
          type="color"
          id="color"
          value={color}
          onChange={(e) => setColor(e.target.value)}
          className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <div className="mb-4">
        <label className="block text-gray-700 font-medium mb-2" htmlFor="description">
          Description
        </label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Enter category description"
        />
      </div>
      <div className="flex justify-end gap-3">
        <button
          type="button"
          onClick={handleCancel}
            className="px-4 py-2 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-300"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600"
        >
          {isEditing ? "Update Category" : "Create Category"}
        </button>
        </div>
    </form>
  );
}