package com.arka.AceleraTI_ArkaStore.service;

import com.arka.AceleraTI_ArkaStore.model.Notification;
import com.arka.AceleraTI_ArkaStore.model.Order;
import com.arka.AceleraTI_ArkaStore.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public Notification create(Notification n) {
        return notificationRepository.save(n);
    }

    public List<Notification> listAll() {
        return notificationRepository.findAll();
    }

    public void notifyOrderStatus(Long orderId, String email, String statusMessage) {
        if (email == null || email.isBlank()) return;

        Notification n = new Notification();
        n.setOrderId(orderId);
        n.setEmail(email);
        n.setMessage("Order " + orderId + " status changed to " + statusMessage);
        notificationRepository.save(n);

        sendEmail(email, "Order update", n.getMessage());
    }

    public void sendOrderStatusChangeNotification(Order order) {
        if (order == null || order.getCustomer() == null) return;

        String email = order.getCustomer().getEmail();
        if (email == null || email.isBlank()) return;

        String message = "El estado de tu pedido #" + order.getId() +
                " ha cambiado a: " + order.getStatus().name();

        Notification notification = new Notification();
        notification.setOrderId(order.getId());
        notification.setMessage(message);
        notification.setEmail(email);
        notificationRepository.save(notification);

        sendEmail(email, "Actualización de tu pedido ArkaStore", message);
    }

    public void sendCartReminder(Long cartId, String email) {
        if (email == null || email.isBlank()) return;

        Notification n = new Notification();
        n.setOrderId(cartId);
        n.setEmail(email);
        n.setMessage("Tienes productos olvidados en tu carrito. ¡Vuelve a ArkaStore y completa tu compra!");
        notificationRepository.save(n);

        sendEmail(email, "Recordatorio de carrito", n.getMessage());
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("Error al enviar correo a " + to + ": " + e.getMessage());
        }
    }
}
