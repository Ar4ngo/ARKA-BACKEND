package com.arka.AceleraTI_ArkaStore.service;

import com.arka.AceleraTI_ArkaStore.dto.ProductDto;
import com.arka.AceleraTI_ArkaStore.model.Category;
import com.arka.AceleraTI_ArkaStore.model.Product;
import com.arka.AceleraTI_ArkaStore.model.StockHistory;
import com.arka.AceleraTI_ArkaStore.repository.CategoryRepository;
import com.arka.AceleraTI_ArkaStore.repository.ProductRepository;
import com.arka.AceleraTI_ArkaStore.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          StockHistoryRepository stockHistoryRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        if (dto.getCategory() == null || dto.getCategory().isBlank()) {
            throw new IllegalArgumentException("Category is required");
        }

        Category category = categoryRepository.findByName(dto.getCategory()).orElse(null);
        if (category == null) {
            category = new Category(dto.getCategory());
            category = categoryRepository.saveAndFlush(category); 
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock() != null ? dto.getStock() : 0);
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    public List<ProductDto> listAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateStock(Long productId, Integer newStock, String reason) {
        if (newStock == null || newStock < 0)
            throw new IllegalArgumentException("Stock value must be zero or greater");

        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Integer prev = p.getStock();
        p.setStock(newStock);
        productRepository.save(p);

        StockHistory sh = new StockHistory();
        sh.setProduct(p);
        sh.setPreviousStock(prev);
        sh.setNewStock(newStock);
        sh.setReason(reason != null ? reason : "Manual stock update");
        stockHistoryRepository.save(sh);

        return toDto(p);
    }

    public List<ProductDto> lowStock(Integer threshold) {
        int t = (threshold != null && threshold > 0) ? threshold : 10;
        return productRepository.findByStockLessThan(t)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Product findEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setCategory(p.getCategory() != null ? p.getCategory().getName() : null);
        return dto;
    }
}