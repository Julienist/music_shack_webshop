package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.CategoryDTO;
import com.duckstudios.webshopapi.models.Category;
import com.duckstudios.webshopapi.services.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDAO {

    private final CategoryRepository categoryRepository;
    private final EntityValidator entityValidator;

    public CategoryDAO(CategoryRepository categoryRepository, EntityValidator entityValidator) {
        this.categoryRepository = categoryRepository;
        this.entityValidator = entityValidator;
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(long id) {
        entityValidator.checkIfIdExists(id, categoryRepository, "Category");
        return this.categoryRepository.findById(id);
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.name);
        this.categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        Category category = entityValidator.checkIfIdExists(id, categoryRepository, "Category");
        this.categoryRepository.delete(category);
    }

//    private <T, ID> T checkIfIdExists(ID id, JpaRepository<T, ID> repository) {
//        return repository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Category" + " met dat id bestaat niet!"));
//    }
}
