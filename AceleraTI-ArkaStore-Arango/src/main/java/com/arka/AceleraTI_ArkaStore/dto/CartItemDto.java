package com.arka.AceleraTI_ArkaStore.dto;

public class CartItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subtotal;

    public CartItemDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
        calculateSubtotal();
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { 
        this.price = price; 
        calculateSubtotal(); 
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

  
    public void calculateSubtotal() {
        if (price != null && quantity != null) {
            this.subtotal = price * quantity;
        } else {
            this.subtotal = null;
        }
    }
}
