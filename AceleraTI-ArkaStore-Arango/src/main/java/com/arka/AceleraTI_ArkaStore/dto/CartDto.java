package com.arka.AceleraTI_ArkaStore.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CartDto {
    private Long id;
    private Long customerId;
    private LocalDateTime createdAt;
    private boolean checkedOut;
    private List<CartItemDto> items;

    public CartDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isCheckedOut() { return checkedOut; }
    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }
}
