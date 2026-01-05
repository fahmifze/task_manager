import { Task } from '../types/task';

// Props interface for TaskItem component
interface TaskItemProps {
  task: Task; // Task data to display
  onDelete: (id: number) => void; // Called when delete clicked
  onToggle: (id: number) => void; // Called when checkbox toggled
  onEdit: (task: Task) => void; // Called when edit clicked
}

// Component to display a single task
export function TaskItem({ task, onDelete, onToggle, onEdit }: TaskItemProps) {
  // Format date string to readable format
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  return (
    <div
      className={`p-4 border rounded-lg shadow-sm mb-3 ${task.completed ? 'bg-gray-50 opacity-75' : 'bg-white'} hover:shadow-md transition-shadow`}
    >
      {/* Task Header: Checkbox + Content */}
      <div className="flex items-start gap-3">
        <input
          type="checkbox"
          checked={task.completed}
          onChange={() => onToggle(task.id)} // Toggle completion on change
          className="mt-1 h-5 w-5 cursor-pointer"
        />

        <div className="flex-1">
          {/* Task Title - strikethrough if completed */}
          <h3 className={`font-semibold text-lg ${task.completed ? 'line-through text-gray-500' : 'text-gray-800'}`}>
            {task.title}
          </h3>

          {/* Task Description - only show if exists */}
          {task.description && (
            <p className="text-gray-600 mt-1">{task.description}</p>
          )}

          {/* Creation Date */}
          <p className="text-sm text-gray-400 mt-2">
            Created: {formatDate(task.createdAt)}
          </p>
        </div>
      </div>

      {/* Action Buttons */}
      <div className="flex gap-2 mt-3 justify-end">
        <button
          onClick={() => onEdit(task)}
          className="px-3 py-1 text-sm text-blue-600 hover:bg-blue-50 rounded"
        >
          Edit
        </button>

        <button
          onClick={() => onDelete(task.id)}
          className="px-3 py-1 text-sm text-red-600 hover:bg-red-50 rounded"
        >
          Delete
        </button>
      </div>
    </div>
  );
}
