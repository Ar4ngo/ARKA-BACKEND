package com.arka.AceleraTI_ArkaStore.controller;

import com.arka.AceleraTI_ArkaStore.dto.OrderRequestDto;
import com.arka.AceleraTI_ArkaStore.model.Customer;
import com.arka.AceleraTI_ArkaStore.model.Order;
import com.arka.AceleraTI_ArkaStore.model.OrderStatus;
import com.arka.AceleraTI_ArkaStore.service.NotificationService;
import com.arka.AceleraTI_ArkaStore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final NotificationService notificationService;

    public OrderController(OrderService orderService, NotificationService notificationService) {
        this.orderService = orderService;
        this.notificationService = notificationService;
    }

 
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto dto) {
        try {
            Customer c = new Customer();
            c.setId(dto.getCustomerId());

            Order o = orderService.createOrder(dto, c);

            return ResponseEntity.ok(o);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la orden: " + e.getMessage());
        }
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyOrder(@PathVariable Long id, @RequestBody OrderRequestDto dto) {
        try {
            Order updated = orderService.updateOrder(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al modificar la orden: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            Order order = orderService.changeStatus(id, status);

            notificationService.sendOrderStatusChangeNotification(order);

            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cambiar estado de la orden: " + e.getMessage());
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Orden no encontrada: " + e.getMessage());
        }
    }
}
