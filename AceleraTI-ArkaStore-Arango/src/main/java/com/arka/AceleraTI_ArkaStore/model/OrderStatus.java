package com.arka.AceleraTI_ArkaStore.model;


public enum OrderStatus {
    PENDING("Pendiente"),
    CONFIRMED("Confirmada"),
    IN_DISPATCH("En despacho"),
    DELIVERED("Entregada");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
