package com.bank.orders_service.client;

import com.bank.orders_service.dto.ProductDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "products-service", url = "http://localhost:8080/api/products")
public interface ProductClient {

    //Get product by id
    @GetMapping("/{id}")
    default Optional<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(getProduct(id));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @GetMapping("/{id}")
    ProductDTO getProduct(@PathVariable Long id);
}
