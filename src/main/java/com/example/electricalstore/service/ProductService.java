package com.example.electricalstore.service;

import com.example.electricalstore.exception.ResourceNotFoundException;
import com.example.electricalstore.model.Product;
import com.example.electricalstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() { return repo.findAll(); }

    public Product getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public Product create(Product p) { return repo.save(p); }

    public Product update(String id, Product p) {
        Product existing = getById(id);
        existing.setName(p.getName());
        existing.setDescription(p.getDescription());
        existing.setPrice(p.getPrice());
        existing.setStock(p.getStock());
        existing.setImageUrl(p.getImageUrl());
        return repo.save(existing);
    }

    public void delete(String id) {
        Product existing = getById(id);
        repo.delete(existing);
    }
}
