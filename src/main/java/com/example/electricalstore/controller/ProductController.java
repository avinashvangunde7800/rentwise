package com.example.electricalstore.controller;

import com.example.electricalstore.model.Product;
import com.example.electricalstore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService svc;

    public ProductController(ProductService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Product> all() {
        return svc.getAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable String id) {
        return svc.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return svc.create(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product p) {
        return svc.update(id, p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
