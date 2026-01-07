import { Tag } from '../types/tag';
import { useState, useEffect } from 'react';

// Props interface for TagForm component
interface TagFormProps {
  tag?: Tag;
  onSubmit: (formdata: { name: string }) => void;
  onCancel: () => void;
}
// Form component for creating and editing tags
export function TagForm({ tag, onSubmit, onCancel }: TagFormProps) {
  const [name, setName] = useState(''); // Name input state
  const isEditing = !!tag; // Check if in edit mode
    // Populate form when editing, clear when creating
    useEffect(() => {
    if (tag) {
      setName(tag.name);
    } else {
      setName('');
    }
    }, [tag]); // Re-run when tag changes
    // Handle form submission
    const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent page reload
    if (!name.trim()) {
      alert('Name is required');
      return;
    }
    const formData = {
      name: name.trim(),
    };
    onSubmit(formData); // Call onSubmit with form data
    if (!tag) {
      setName(''); // Clear form only when creating new tag
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
        {isEditing ? 'Edit Tag' : 'New Tag'}
      </h2>
      <div className="mb-4">
        <label className="block text-gray-700 font-medium mb-2" htmlFor="name">
          Name
        </label>
        <input
          type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
        </div>
        <div className="flex justify-end space-x-4">
        <button
          type="button"
            onClick={handleCancel}
            className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
        >
          Cancel
        </button>
        <button
          type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
        >
          {isEditing ? 'Update Tag' : 'Create Tag'}
        </button>
        </div>
    </form>
  );
}