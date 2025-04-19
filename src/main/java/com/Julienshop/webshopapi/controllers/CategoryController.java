package com.Julienshop.webshopapi.controllers;

import com.Julienshop.webshopapi.dao.CategoryDAO;
import com.Julienshop.webshopapi.dto.CategoryDTO;
import com.Julienshop.webshopapi.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(this.categoryDAO.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable long id) {
        return ResponseEntity.ok(this.categoryDAO.getCategoryById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO){
        this.categoryDAO.createCategory(categoryDTO);
        return ResponseEntity.ok("New category was created!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        this.categoryDAO.deleteCategory(id);
        return ResponseEntity.ok("Category deleted");
    }

}
