package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dto.ProductDTO;
import com.duckstudios.webshopapi.dao.ProductDAO;
import com.duckstudios.webshopapi.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/products")
public class ProductController {
    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.productDAO.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable long id) {
        Optional<Product> product = this.productDAO.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO ){
        // De gebruiker moet een set aan data krijgen. → naam van product en description van product.
        this.productDAO.createProduct(productDTO);
        // De DAO vragen om de task te gaan maken en weg te schrijven naar de database.
        // Debug message if writing to db has gone good.
        return ResponseEntity.ok("Het is gelukt!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        this.productDAO.updateProductByID(id, productDTO);
        return ResponseEntity.ok("Updated product with id " + id);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<String> checkProduct(@PathVariable long id) {
        this.productDAO.setProductAvailable(id);
        return ResponseEntity.ok("Finished product with id " + id);
    }

    @PutMapping("/uncheck/{id}")
    public ResponseEntity<String> uncheckProduct(@PathVariable long id) {
        this.productDAO.setProductUnavailable(id);
        return ResponseEntity.ok("Unfinished product with id " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable long id) {
        this.productDAO.deleteProduct(id);
        return ResponseEntity.ok("Deleted product with id " + id);
    }
}

