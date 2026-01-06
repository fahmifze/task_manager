import { Category } from "../types/category";

// Props interface for CategoryItem component
interface CategoryItemProps {
  category: Category; // Category data to display
  onDelete: (id: number) => void; // Called when delete clicked
  onEdit: (category: Category) => void; // Called when edit clicked
}
// Component to display a single category

export function CategoryItem({ category, onDelete, onEdit }: CategoryItemProps) {
  return (
    <div className="p-4 border rounded-lg shadow-sm mb-3 bg-white hover:shadow-md transition-shadow">
      {/* Category Name with Color */}
      <div className="flex items-center gap-2">
        <span
          className="w-4 h-4 rounded-full"
          style={{ backgroundColor: category.color }}
        />
        <h3 className="font-semibold text-lg text-gray-800">
          {category.name}
        </h3>
      </div>

      {/* Description */}
      {category.description && (
        <p className="text-gray-600 text-sm mt-1">{category.description}</p>
      )}

      {/* Action Buttons */}
      <div className="flex gap-2 mt-3 justify-end">
        <button
          onClick={() => onEdit(category)}
          className="px-3 py-1 bg-yellow-500 text-white rounded-md hover:bg-yellow-600"
        >
          Edit
        </button>
        <button
          onClick={() => onDelete(category.id)}
          className="px-3 py-1 bg-red-600 text-white rounded-md hover:bg-red-700"
        >
          Delete
        </button>
      </div>
    </div>
  );
}
