package com.example.products_service.controller;

import com.example.products_service.dto.ProductDTO;
import com.example.products_service.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.products_service.client.ProductClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductClient productClient;

    public ProductController(ProductClient productClient){
        this.productClient = productClient;
    }

    @GetMapping
    public List<ProductDTO> getAllProduct(){
        return productClient.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productClient.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

}
