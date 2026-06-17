package com.Julienshop.webshopapi.dao;

import com.Julienshop.webshopapi.dto.ProductDTO;
import com.Julienshop.webshopapi.models.Category;
import com.Julienshop.webshopapi.models.Product;
import com.Julienshop.webshopapi.services.EntityValidator;
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
        Category category = entityValidator.checkIfIdExists(productDTO.getCategoryId(), categoryRepository, "Category");
        Product product = new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.isAvailable(), productDTO.getImageurl(), productDTO.getAmountInStock(), category);
        this.productRepository.save(product);
    }

    public void updateProductByID(long id, ProductDTO productDTO) {
        Product product = entityValidator.checkIfIdExists(id, productRepository, "Product");
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
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
