package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.ProductDTO;
import com.duckstudios.webshopapi.models.Category;
import com.duckstudios.webshopapi.models.Product;
import com.duckstudios.webshopapi.services.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {

    private final CategoryRepository categoryRepository;
    private final EntityValidator entityValidator;
    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository, CategoryRepository categoryRepository, EntityValidator entityValidator) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.entityValidator = entityValidator;
    }

    public List<Product> getAllProducts(){
        //noinspection UnnecessaryLocalVariable
        List<Product> products = this.productRepository.findAll();
        // Opgesplitst in 2 lines, voor leesbaarheid
        return products;
    }

    public Optional<Product> getProductById(long id) {
        entityValidator.checkIfIdExists(id, productRepository, "Product");
        //noinspection UnnecessaryLocalVariable
        Optional<Product> product = this.productRepository.findById(id);
        // Opgesplitst in 2 lines, voor leesbaarheid
        return product;
    }

    public void createProduct(ProductDTO productDTO) {
        Category category = entityValidator.checkIfIdExists(productDTO.categoryId, categoryRepository, "Category");
        Product product = new Product(productDTO.name, productDTO.description, productDTO.price, productDTO.isAvailable, productDTO.imageurl, productDTO.amountInStock, category);
        this.productRepository.save(product);
    }

    public void updateProductByID(long id, ProductDTO productDTO) {
        Product product = entityValidator.checkIfIdExists(id, productRepository, "Product");
        product.setName(productDTO.name);
        product.setDescription(productDTO.description);
        this.productRepository.save(product);
    }

    public void setProductAvailable(long id) {
        Product product = entityValidator.checkIfIdExists(id, productRepository, "Product");
        product.setAvailable(true);
        this.productRepository.save(product);
    }

    public void setProductUnavailable(long id) {
        Product product = entityValidator.checkIfIdExists(id, productRepository, "Product");
        product.setAvailable(false);
        this.productRepository.save(product);
    }

    public void deleteProduct(long id) {
        Product product = entityValidator.checkIfIdExists(id, productRepository, "Product");
        this.productRepository.delete(product);
    }
}
