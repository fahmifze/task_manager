import { Tag } from '../types/tag'; // Import Tag type

// Props interface for TagItem component
interface TagItemProps {
  tag: Tag; // Tag data to display
  onDelete: (id: number) => void; // Called when delete clicked
  onEdit: (tag: Tag) => void; // Called when edit clicked
}
// Component to display a single tag
export function TagItem({ tag, onDelete, onEdit }: TagItemProps) {
  return (
    <div className="p-4 border rounded-lg shadow-sm mb-3 bg-white hover:shadow-md transition-shadow">
      {/* Tag Name */}
      <h3 className="font-semibold text-lg text-gray-800">{tag.name}</h3>
        {/* Action Buttons */}
        <div className="flex gap-2 mt-3 justify-end">
        <button
          onClick={() => onEdit(tag)}
          className="px-3 py-1 bg-yellow-500 text-white rounded-md hover:bg-yellow-600"
        >
          Edit
        </button>
        <button
          onClick={() => onDelete(tag.id)}
          className="px-3 py-1 bg-red-600 text-white rounded-md hover:bg-red-700"
        >
          Delete
        </button>
        </div>
    </div>
  );
}