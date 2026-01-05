import { Task } from '../types/task';
import { TaskItem } from './TaskItem';

// Props interface for TaskList component
interface TaskListProps {
  tasks: Task[]; // Array of tasks to display
  onDelete: (id: number) => void; // Callback for delete action
  onToggle: (id: number) => void; // Callback for toggle action
  onEdit: (task: Task) => void; // Callback for edit action
  loading?: boolean; // Show loading state
}

// Component to render a list of tasks
export function TaskList({ tasks, onDelete, onToggle, onEdit, loading }: TaskListProps) {
  // Show loading spinner while fetching data
  if (loading) {
    return (
      <div className="text-center py-8">
        <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-500 border-t-transparent"></div>
        <p className="mt-2 text-gray-600">Loading tasks...</p>
      </div>
    );
  }

  // Show empty state when no tasks exist
  if (tasks.length === 0) {
    return (
      <div className="text-center py-8 bg-gray-50 rounded-lg">
        <p className="text-gray-500 text-lg">No tasks yet!</p>
        <p className="text-gray-400 mt-1">Create your first task above.</p>
      </div>
    );
  }

  // Render task list
  return (
    <div className="space-y-3">
      {tasks.map((task) => (
        <TaskItem
          key={task.id} // Unique key for React list rendering
          task={task}
          onDelete={onDelete}
          onToggle={onToggle}
          onEdit={onEdit}
        />
      ))}
    </div>
  );
}
