import { TasksPage } from './pages/TasksPage';
import { CategoriesPage } from './pages/CategoriesPage';
import { TagsPage } from './pages/TagsPage';
import { useState } from 'react';
import { Sidebar } from './components/Sidebar';
// Root component of the React application
function App() {
  const [activePage, setActivePage] = useState<'tasks' | 'categories' | 'tags'>('tasks');

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

export default App;
