import { useState, useEffect } from 'react';
import { Task, TaskFormData } from '../types/task';
import { taskService } from '../services/taskService';
import { TaskForm } from '../components/TaskForm';
import { TaskList } from '../components/TaskList';

// Main page component - manages state and API calls
export function TasksPage() {
  const [tasks, setTasks] = useState<Task[]>([]); // List of tasks
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState<string | null>(null); // Error message
  const [taskToEdit, setTaskToEdit] = useState<Task | undefined>(undefined); // Task being edited

  // Fetch tasks on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  // Fetch all tasks from API
  const fetchTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskService.getAll();
      setTasks(data);
    } catch (err) {
      setError('Failed to load tasks. Is the server running?');
      console.error('Error fetching tasks:', err);
    } finally {
      setLoading(false);
    }
  };

  // Create a new task
  const handleCreateTask = async (formData: TaskFormData) => {
    try {
      const newTask = await taskService.create(formData);
      setTasks([newTask, ...tasks]); // Add to beginning of list
    } catch (err) {
      setError('Failed to create task');
      console.error('Error creating task:', err);
    }
  };

  // Update existing task
  const handleUpdateTask = async (formData: TaskFormData) => {
    if (!taskToEdit) return;
    try {
      const updatedTask = await taskService.update(taskToEdit.id, formData);
      setTasks(tasks.map(task => task.id === updatedTask.id ? updatedTask : task)); // Replace in list
      setTaskToEdit(undefined); // Exit edit mode
    } catch (err) {
      setError('Failed to update task');
      console.error('Error updating task:', err);
    }
  };

  // Delete task with confirmation
  const handleDeleteTask = async (id: number) => {
    if (!confirm('Are you sure you want to delete this task?')) return;
    try {
      await taskService.delete(id);
      setTasks(tasks.filter(task => task.id !== id)); // Remove from list
    } catch (err) {
      setError('Failed to delete task');
      console.error('Error deleting task:', err);
    }
  };

  // Toggle task completion status
  const handleToggleTask = async (id: number) => {
    try {
      const toggledTask = await taskService.toggle(id);
      setTasks(tasks.map(task => task.id === toggledTask.id ? toggledTask : task));
    } catch (err) {
      setError('Failed to toggle task');
      console.error('Error toggling task:', err);
    }
  };

  // Enter edit mode for a task
  const handleEditTask = (task: Task) => {
    setTaskToEdit(task);
  };

  // Cancel edit mode
  const handleCancelEdit = () => {
    setTaskToEdit(undefined);
  };

  // Handle form submission (create or update)
  const handleFormSubmit = (formData: TaskFormData) => {
    if (taskToEdit) {
      handleUpdateTask(formData);
    } else {
      handleCreateTask(formData);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-2xl mx-auto py-8 px-4">
        <h1 className="text-3xl font-bold text-gray-800 mb-8 text-center">
          Task Manager
        </h1>

        {/* Error Alert */}
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
            <button onClick={() => setError(null)} className="float-right font-bold">
              ×
            </button>
          </div>
        )}

        {/* Task Form */}
        <TaskForm
          onSubmit={handleFormSubmit}
          taskToEdit={taskToEdit}
          onCancel={handleCancelEdit}
        />

        {/* Task List */}
        <TaskList
          tasks={tasks}
          onDelete={handleDeleteTask}
          onToggle={handleToggleTask}
          onEdit={handleEditTask}
          loading={loading}
        />
      </div>
    </div>
  );
}
