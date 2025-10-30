package com.arka.AceleraTI_ArkaStore.dto;

public class DetailOrderDto {
    private Long productId;
    private Integer quantity;

    public DetailOrderDto(){}
    public Long getProductId(){return productId;}
    public void setProductId(Long productId){this.productId=productId;}
    public Integer getQuantity(){return quantity;}
    public void setQuantity(Integer quantity){this.quantity=quantity;}
}
