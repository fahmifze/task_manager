import { Category, CategoryFormData } from '../types/category';
import { CategoryItem } from '../components/CategoryItem';
import { CategoryForm } from '../components/CategoryForm';
import { categoryService } from '../services/categoryService';
import { useState, useEffect } from 'react';

// Component to display and manage list of categories
export function CategoriesPage() {
    const [categories, setCategories] = useState<Category[]>([]); // State to hold list of categories
    const [loading, setLoading] = useState(true); // State to track loading status
    const [error, setError] = useState<string | null>(null);  // State to hold error messages
    const [categoryToEdit, setCategoryToEdit] = useState<Category | undefined>(undefined);  // State to hold category being edited
    const [showForm, setShowForm] = useState(false); // State to control form visibility

    // Fetch categories on component mount
    useEffect(() => {
        fetchCategories();
    }, []); // Empty dependency array means this runs once on mount
    const fetchCategories = async () => {
        try { // Start loading
            setLoading(true);
            setError(null); // Clear previous errors
            const data = await categoryService.getAll(); // Fetch categories from API
            setCategories(data); // Store categories in state
        } catch (err) { // Handle errors
            setError('Failed to load categories. Is the server running?'); // Set error message
            console.error('Error fetching categories:', err); // Log error for debugging
        } finally {
            setLoading(false); // Stop loading
        }
    };
    const handleCreateCategory = async (formData: CategoryFormData) => {
        try { // Create new category via API
            const newCategory = await categoryService.create(formData); // add new category
            setCategories([newCategory, ...categories]); // Prepend new category to state list
            setShowForm(false); // Hide form after creation
        } catch (err) {// Handle errors
            setError('Failed to create category');
            console.error('Error creating category:', err);
        }
    };
    const handleUpdateCategory = async (formData: CategoryFormData) => {
        if (!categoryToEdit) return;
        try { // Update existing category via API
            const updatedCategory = await categoryService.update(categoryToEdit.id, formData); // Update category
            setCategories(categories.map(cat => cat.id === categoryToEdit.id ? updatedCategory : cat)); // Update category in state list
            setCategoryToEdit(undefined); // Clear edit state
        } catch (err) { // Handle errors
            setError('Failed to update category');
            console.error('Error updating category:', err);
        }
    };
    const handleDeleteCategory = async (id: number) => {
        if (!confirm('Are you sure you want to delete this category?')) return;
        try { // Delete category via API
            await categoryService.delete(id); // Delete category
            setCategories(categories.filter(cat => cat.id !== id)); // Remove category from state list
        } catch (err) { // Handle errors
            setError('Failed to delete category');
            console.error('Error deleting category:', err);
        }
    };
    const handleCancel = () => {
        setCategoryToEdit(undefined); // Clear edit state
        setShowForm(false); // Hide form
    };
    return (
        <div className="max-w-3xl mx-auto">
            <h1 className="text-3xl font-bold mb-4">Categories</h1>
            {error && <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>}
            {loading ? (
                <div>Loading categories...</div>
            ) : (
                <>
                    <button
                        onClick={() => { setShowForm(true); setCategoryToEdit(undefined); }}
                        className="mb-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-offset-2"
                    >
                        New Category
                    </button>
                    {(showForm || categoryToEdit) && (
                        <CategoryForm
                            category={categoryToEdit}
                            onSubmit={categoryToEdit ? handleUpdateCategory : handleCreateCategory}
                            onCancel={handleCancel}
                        />
                    )}
                    <div className="space-y-4">
                        {categories.map(category => (
                            <CategoryItem
                                key={category.id}
                                category={category}
                                onDelete={handleDeleteCategory}
                                onEdit={(cat) => { setCategoryToEdit(cat); setShowForm(true); }}
                            />
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}