package com.arka.AceleraTI_ArkaStore.dto;

import java.util.List;

public class OrderRequestDto {
    private Long customerId;
    private List<DetailOrderDto> items;

    public OrderRequestDto(){}
    public Long getCustomerId(){return customerId;}
    public void setCustomerId(Long customerId){this.customerId=customerId;}
    public List<DetailOrderDto> getItems(){return items;}
    public void setItems(List<DetailOrderDto> items){this.items=items;}
}
