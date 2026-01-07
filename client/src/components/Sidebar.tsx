interface SidebarProps {
  activePage: 'tasks' | 'categories' | 'tags';
  onPageChange: (page: 'tasks' | 'categories' | 'tags') => void;
}

export function Sidebar({ activePage, onPageChange }: SidebarProps) {
  const menuItems = [
    { id: 'tasks', label: 'Tasks' },
    { id: 'categories', label: 'Categories' },
    { id: 'tags', label: 'Tags' },
  ] as const;

  return (
    <aside className="w-64 bg-blue-600 text-white min-h-screen p-4">
      <h2 className="text-xl font-bold mb-6">Menu</h2>
      <nav>
        <ul className="space-y-2">
          {menuItems.map((item) => (
            <li key={item.id}>
              <button
                onClick={() => onPageChange(item.id)}
                className={`w-full text-left px-4 py-3 rounded-lg flex items-center gap-3 transition-colors ${
                  activePage === item.id
                    ? 'bg-blue-700 font-semibold'
                    : 'hover:bg-blue-500'
                }`}
              >
                <span>{item.label}</span>
              </button>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
}
