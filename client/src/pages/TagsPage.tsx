import { Tag } from '../types/tag'; // Import Tag type
import { TagItem } from '../components/Tagitem';
import { TagForm } from '../components/TagForm';
import { useState, useEffect } from 'react';
import { tagService } from '../services/tagService';

// Component to display and manage list of tags
export function TagsPage() {
    const [tags, setTags] = useState<Tag[]>([]); // State to hold list of tags
    const [loading, setLoading] = useState(true); // State to track loading status
    const [error, setError] = useState<string | null>(null);  // State to hold error messages
    const [tagToEdit, setTagToEdit] = useState<Tag | undefined>(undefined);  // State to hold tag being edited
    const [showForm, setShowForm] = useState(false); // State to control form visibility
    // Fetch tags on component mount
    useEffect(() => {
        fetchTags();
    }, []); // Empty dependency array means this runs once on mount
    const fetchTags = async () => {
        try { // Start loading
            setLoading(true);
            setError(null); // Clear previous errors
            const data = await tagService.getAll(); // Fetch tags from API
            setTags(data); // Store tags in state
        } catch (err) { // Handle errors
            setError('Failed to load tags. Is the server running?'); // Set error message
            console.error('Error fetching tags:', err); // Log error for debugging
        } finally {
            setLoading(false); // Stop loading
        }
    };
    const handleCreateTag = async (formData : { name: string }) => {
        try { // Create new tag via API
            const newTag = await tagService.create({ name: formData.name }); // add new tag
            setTags([newTag, ...tags]); // Prepend new tag to state list
            setShowForm(false); // Hide form after creation
        } catch (err) {// Handle errors
            setError('Failed to create tag');
            console.error('Error creating tag:', err);
        }
    };
    const handleUpdateTag = async (formData : { name: string }) => {
        if (!tagToEdit) return;
        try { // Update existing tag via API
            const updatedTag = await tagService.update(tagToEdit.id, { name: formData.name }); // Update tag
            setTags(tags.map(t => t.id === tagToEdit.id ? updatedTag : t)); // Update tag in state list
            setTagToEdit(undefined); // Clear edit state
        } catch (err) { // Handle errors
            setError('Failed to update tag');
            console.error('Error updating tag:', err);
        }
    };
    const handleDeleteTag = async (id: number) => {
        if (!confirm('Are you sure you want to delete this tag?')) return;
        try { // Delete tag via API
            await tagService.delete(id); // Delete tag
            setTags(tags.filter(t => t.id !== id)); // Remove tag from state list
        } catch (err) { // Handle errors
            setError('Failed to delete tag');
            console.error('Error deleting tag:', err);
        }
    };
    return (
        <div>
            <h2 className="text-2xl font-bold mb-4">Tags</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            {loading ? (
                <div>Loading tags...</div>
            ) : (
                <div>
                    <button className="mb-4 px-4 py-2 bg-blue-500 text-white rounded" onClick={() => setShowForm(true)}>
                        Add Tag
                    </button>
                    {showForm && (
                        <TagForm
                            tag ={tagToEdit}
                            onSubmit={tagToEdit ? handleUpdateTag : handleCreateTag}
                            onCancel={() => { setTagToEdit(undefined); setShowForm(false); }}
                        />
                    )}
                    <ul>
                        {tags.map(tag => (
                            <TagItem
                                key={tag.id}
                                tag={tag}
                                onEdit={(tag) => { setTagToEdit(tag); setShowForm(true); }}
                                onDelete={() => handleDeleteTag(tag.id)}
                            />
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}