package com.taskmanager.service;

import com.taskmanager.dto.TagDTO;
import com.taskmanager.entity.Tag;
import com.taskmanager.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marks this as a service component for business logic
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public TagDTO getTagById(Long id) {
        return tagRepository.findById(id)
                .map(TagDTO::fromEntity)
                .orElse(null);
    }

    public TagDTO createTag(TagDTO tagDTO) {
        Tag tag = tagDTO.toEntity();
        Tag savedTag = tagRepository.save(tag);
        return TagDTO.fromEntity(savedTag);
    }

    public TagDTO updateTag(Long id, TagDTO tagDTO) {
        return tagRepository.findById(id)
                .map(existingTag -> {
                    existingTag.setName(tagDTO.getName());
                    Tag updatedTag = tagRepository.save(existingTag);
                    return TagDTO.fromEntity(updatedTag);
                })
                .orElse(null);
    }

    public boolean deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
