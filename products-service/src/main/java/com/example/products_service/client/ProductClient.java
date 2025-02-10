package com.example.products_service.client;

import com.example.products_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-client", url = "https://fakestoreapi.com")
public interface ProductClient {
    @GetMapping("/products")
    List<ProductDTO> getAllProducts();

    @GetMapping("/products/{id}")
    Optional<ProductDTO> getProductById(@PathVariable("id") Long id);
}
