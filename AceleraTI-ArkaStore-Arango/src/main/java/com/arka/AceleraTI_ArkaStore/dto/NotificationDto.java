package com.arka.AceleraTI_ArkaStore.dto;

public class NotificationDto {
    private Long id;
    private Long orderId;
    private String message;
    private String email;

    public NotificationDto(){}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Long getOrderId(){return orderId;}
    public void setOrderId(Long orderId){this.orderId=orderId;}
    public String getMessage(){return message;}
    public void setMessage(String message){this.message=message;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
}
