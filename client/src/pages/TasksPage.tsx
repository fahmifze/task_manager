import { useState, useEffect } from 'react'; // to store data and run code on mount
import { Task, TaskFormData } from '../types/task'; // import Task and TaskFormData types
import { taskService } from '../services/taskService'; // import taskService for API calls
import { TaskForm } from '../components/TaskForm'; // import TaskForm component
import { TaskList } from '../components/TaskList'; // import TaskList component
import { useAuth } from '../context/AuthContext';


export function TasksPage() {
  const [tasks, setTasks] = useState<Task[]>([]); // State to hold list of tasks
  const [loading, setLoading] = useState(true); // State to track loading status
  const [error, setError] = useState<string | null>(null);  // State to hold error messages
  const [taskToEdit, setTaskToEdit] = useState<Task | undefined>(undefined);  // State to hold task being edited
  const {user, logout} = useAuth();
  // Fetch tasks on component mount
  useEffect(() => {
    fetchTasks(); 
  }, []); // Empty dependency array means this runs once on mount

  const fetchTasks = async () => {
    try { // Start loading
      setLoading(true); 
      setError(null); // Clear previous errors
      const data = await taskService.getAll(); // Fetch tasks from API
      setTasks(data); // Store tasks in state
    } catch (err) { // Handle errors
      setError('Failed to load tasks. Is the server running?'); // Set error message
      console.error('Error fetching tasks:', err); // Log error for debugging
    } finally {
      setLoading(false); // Stop loading
    }
  };

  const handleCreateTask = async (formData: TaskFormData) => {
    try { // Create new task via API
      const newTask = await taskService.create(formData); // add new task 
      setTasks([newTask, ...tasks]); // Prepend new task to state list
    } catch (err) {// Handle errors
      setError('Failed to create task');
      console.error('Error creating task:', err);
    }
  };

  const handleUpdateTask = async (formData: TaskFormData) => {
    if (!taskToEdit) return;
    try {
      const updatedTask = await taskService.update(taskToEdit.id, formData);
      setTasks(tasks.map(task => task.id === updatedTask.id ? updatedTask : task));
      setTaskToEdit(undefined);
    } catch (err) {
      setError('Failed to update task');
      console.error('Error updating task:', err);
    }
  };

  const handleDeleteTask = async (id: number) => {
    if (!confirm('Are you sure you want to delete this task?')) return;
    try {
      await taskService.delete(id);
      setTasks(tasks.filter(task => task.id !== id));
    } catch (err) {
      setError('Failed to delete task');
      console.error('Error deleting task:', err);
    }
  };

  const handleToggleTask = async (id: number) => {
    try {
      const toggledTask = await taskService.toggle(id);
      setTasks(tasks.map(task => task.id === toggledTask.id ? toggledTask : task));
    } catch (err) {
      setError('Failed to toggle task');
      console.error('Error toggling task:', err);
    }
  };

  const handleEditTask = (task: Task) => {
    setTaskToEdit(task);
  };

  const handleCancelEdit = () => {
    setTaskToEdit(undefined);
  };

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

        {/* Header with User Info & Logout */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">
            Task Manager
          </h1>
          <div className="flex items-center gap-4">
            <span className="text-gray-600">
              Hello, {user?.username}!
            </span>
            <button
              onClick={logout}
              className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
            >
              Logout
            </button>
          </div>
        </div>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
            <button onClick={() => setError(null)} className="float-right font-bold">
              ×
            </button>
          </div>
        )}

        <TaskForm
          onSubmit={handleFormSubmit}
          taskToEdit={taskToEdit}
          onCancel={handleCancelEdit}
        />

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
// This page component manages the overall task list state and interactions.
// It fetches tasks from the API, handles creating, updating, deleting, and toggling tasks.
// It uses TaskForm for task creation/editing and TaskList to display the list of tasks.
// Error handling and loading states are also managed here.
// sends data to service via API calls and passes data down to child components via props.