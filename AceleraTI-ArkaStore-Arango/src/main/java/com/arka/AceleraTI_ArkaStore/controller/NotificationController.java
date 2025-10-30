package com.arka.AceleraTI_ArkaStore.controller;

import com.arka.AceleraTI_ArkaStore.dto.NotificationDto;
import com.arka.AceleraTI_ArkaStore.model.Notification;
import com.arka.AceleraTI_ArkaStore.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService){this.notificationService = notificationService;}

    @GetMapping
    public ResponseEntity<List<Notification>> list(){ return ResponseEntity.ok(notificationService.listAll()); }

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody NotificationDto dto){
        Notification n = new Notification();
        n.setOrderId(dto.getOrderId());
        n.setEmail(dto.getEmail());
        n.setMessage(dto.getMessage());
        return ResponseEntity.ok(notificationService.create(n));
    }
}
