import { useState, useEffect } from 'react';
import { Task, TaskFormData } from '../types/task';

// Props interface for TaskForm component
interface TaskFormProps {
  onSubmit: (task: TaskFormData) => void; // Called when form is submitted
  taskToEdit?: Task; // If provided, form is in edit mode
  onCancel?: () => void; // Called when edit is cancelled
}

// Form component for creating and editing tasks
export function TaskForm({ onSubmit, taskToEdit, onCancel }: TaskFormProps) {
  const [title, setTitle] = useState(''); // Title input state
  const [description, setDescription] = useState(''); // Description input state
  const [dueDate, setDueDate] = useState<string | undefined>(); // Due date input state

  // Populate form when editing, clear when creating
  useEffect(() => {
    if (taskToEdit) {
      setTitle(taskToEdit.title);
      setDescription(taskToEdit.description || '');
      setDueDate(taskToEdit.dueDate);
    } else {
      setTitle('');
      setDescription('');
      setDueDate('');
    }
  }, [taskToEdit]); // Re-run when taskToEdit changes

  // Handle form submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent page reload
    if (!title.trim()) {
      alert('Title is required');
      return;
    }
    const formData: TaskFormData = {
      title: title.trim(),
      description: description.trim(),
      dueDate: dueDate ? dueDate : undefined,
    };
    onSubmit(formData);
    if (!taskToEdit) { // Clear form only when creating new task
      setTitle('');
      setDescription('');
      setDueDate('');
    }
  };

  // Handle cancel button click
  const handleCancel = () => {
    setTitle('');
    setDescription('');
    setDueDate('');
    onCancel?.(); // Call onCancel if provided
  };

  const isEditing = !!taskToEdit; // Check if in edit mode

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md mb-6">
      <h2 className="text-xl font-bold mb-4 text-gray-800">
        {isEditing ? 'Edit Task' : 'Create New Task'}
      </h2>

      {/* Title Input */}
      <div className="mb-4">
        <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">
          Title *
        </label>
        <input
          type="text"
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Enter task title"
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          required
        />
      </div>

      {/* Description Input */}
      <div className="mb-4">
        <label htmlFor="description" className="block text-sm font-medium text-gray-700 mb-1">
          Description
        </label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Enter task description (optional)"
          rows={3}
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      {/* Due Date Input */}
      <div className="mb-4">
        <label htmlFor="dueDate" className="block text-sm font-medium text-gray-700 mb-1">
          Due Date
        </label>
        <input
          type="date"
          id="dueDate"
          value={dueDate ? dueDate.split('T')[0] : ''}
          onChange={(e) => setDueDate(e.target.value)}
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      {/* Action Buttons */}
      <div className="flex gap-2">
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
        >
          {isEditing ? 'Update Task' : 'Add Task'}
        </button>

        {isEditing && (
          <button
            type="button"
            onClick={handleCancel}
            className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
          >
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}

//The form doesn't save anything itself - 
// it just collects data and passes it up to the parent component.