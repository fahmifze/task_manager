//Tag entity returned from API (matches backend DTO)

export interface Tag {
  id: number;
  name: string;
  createdAt: string;
}
// Data required to create a new tag
export interface TagFormData {
  name: string;
}
// Partial form data for updates (all fields optional)
export type TagUpdateData = Partial<TagFormData>;
// Generic API response wrapper for tags
export interface TagApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

