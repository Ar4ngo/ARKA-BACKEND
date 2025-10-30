package com.arka.AceleraTI_ArkaStore.controller;

import com.arka.AceleraTI_ArkaStore.dto.ProductDto;
import com.arka.AceleraTI_ArkaStore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto dto){
        try {
            return ResponseEntity.ok(productService.createProduct(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el producto: " + e.getMessage());
        }
    }

   
    @GetMapping
    public ResponseEntity<List<ProductDto>> list() {
        return ResponseEntity.ok(productService.listAll());
    }


    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(
            @PathVariable Long id,
            @RequestParam Integer stock,
            @RequestParam(required = false) String reason) {
        try {
            return ResponseEntity.ok(productService.updateStock(id, stock, reason));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el stock: " + e.getMessage());
        }
    }

    @GetMapping("/lowstock")
    public ResponseEntity<List<ProductDto>> lowStock(@RequestParam(defaultValue = "10") Integer threshold){
        return ResponseEntity.ok(productService.lowStock(threshold));
    }
}
