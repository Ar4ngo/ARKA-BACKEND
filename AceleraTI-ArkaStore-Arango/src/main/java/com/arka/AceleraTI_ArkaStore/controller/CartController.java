package com.arka.AceleraTI_ArkaStore.controller;

import com.arka.AceleraTI_ArkaStore.model.Cart;
import com.arka.AceleraTI_ArkaStore.service.CartService;
import com.arka.AceleraTI_ArkaStore.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final NotificationService notificationService;

    public CartController(CartService cartService, NotificationService notificationService) {
        this.cartService = cartService;
        this.notificationService = notificationService;
    }

 
    @GetMapping("/abandoned")
    public ResponseEntity<List<Cart>> findAbandoned(@RequestParam(defaultValue = "24") int hours) {
        List<Cart> abandoned = cartService.findAbandoned(hours);

        for (Cart cart : abandoned) {
            if (cart.getCustomer() != null && cart.getCustomer().getEmail() != null) {
                notificationService.notifyOrderStatus(
                        cart.getId(),
                        cart.getCustomer().getEmail(),
                        "Tienes productos pendientes en tu carrito. ¡No pierdas tu compra!"
                );
            }
        }

        return ResponseEntity.ok(abandoned);
    }
}
