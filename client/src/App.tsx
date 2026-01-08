import { TasksPage } from './pages/TasksPage';
import { CategoriesPage } from './pages/CategoriesPage';
import { TagsPage } from './pages/TagsPage';
import { useState } from 'react';
import { Sidebar } from './components/Sidebar';
import { AuthPage } from './pages/AuthPage';
import { AuthContextProvider, useAuth } from './context/AuthContext';

// Main content - shows auth page or main app based on login status
function AppContent() {
  const { isAuthenticated, loading } = useAuth();
  const [activePage, setActivePage] = useState<'tasks' | 'categories' | 'tags'>('tasks');

  // Show loading spinner while checking auth status
  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        Loading...
      </div>
    );
  }

  // If not logged in, show auth page (login/register)
  if (!isAuthenticated) {
    return <AuthPage />;
  }

  // If logged in, show main app with sidebar
  return (
    <div className="flex min-h-screen">
      <Sidebar activePage={activePage} onPageChange={setActivePage} />
      <main className="flex-1 p-6 bg-gray-100">
        {activePage === 'tasks' && <TasksPage />}
        {activePage === 'categories' && <CategoriesPage />}
        {activePage === 'tags' && <TagsPage />}
      </main>
    </div>
  );
}

// Root component - wraps everything with AuthProvider
function App() {
  return (
    <AuthContextProvider>
      <AppContent />
    </AuthContextProvider>
  );
}

export default App;
