import { TasksPage } from './pages/TasksPage';
import { CategoriesPage } from './pages/CategoriesPage';
// Root component of the React application
function App() {

  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-blue-600 text-white p-4 shadow-md">
        <h1 className="text-3xl font-bold">Task Manager</h1>
      </header>
      <main className="p-4">
        <TasksPage />
        <CategoriesPage />
      </main>
    </div>
  );
}

export default App;
