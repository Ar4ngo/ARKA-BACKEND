package com.arka.AceleraTI_ArkaStore.service;

import com.arka.AceleraTI_ArkaStore.model.Cart;
import com.arka.AceleraTI_ArkaStore.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findAbandoned(int hours) {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(hours);
        return cartRepository.findByCheckedOutFalseAndCreatedAtBefore(cutoff);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + id));
    }
}
